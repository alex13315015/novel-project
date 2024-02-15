package com.project.novel.service;

import com.project.novel.dto.*;
import com.project.novel.entity.Book;
import com.project.novel.entity.BookLikes;
import com.project.novel.entity.Member;
import com.project.novel.repository.BookLikesRepository;
import com.project.novel.repository.BookRepository;
import com.project.novel.repository.ChapterRepository;
import com.project.novel.repository.MemberRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    @Value("${file.path}")
    private String uploadFolder;
    private final BookRepository bookRepository;
    private final BookLikesRepository bookLikesRepository;
    private final ChapterRepository chapterRepository;
    private final MemberRepository memberRepository;
    private final ChapterService chapterService;
    private final SubscribeService subscribeService;

    @Transactional
    public void write(@Valid BookUploadDto bookUploadDto,
                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        String thumbnailImageFileName = processImageFile(bookUploadDto);

        Book book = bookUploadDto.toEntity(
                bookUploadDto.getBookName(),
                bookUploadDto.getBookIntro(),
                thumbnailImageFileName,
                bookUploadDto.getBookGenre(),
                bookUploadDto.getAgeRating(),
                customUserDetails.getLoggedMember()
        );
        bookRepository.save(book);
    }

    private String processImageFile(BookUploadDto bookUploadDto) {
        if (bookUploadDto.getBookImage() == null || bookUploadDto.getBookImage().isEmpty()) {
            // 이미지를 업로드하지 않은 경우, bookGenre 별 기본 이미지 설정
            return switch (bookUploadDto.getBookGenre()) {
                case ORIENTAL_FANTASY -> "/images/oriental_fantasy_genre1.png";
                case HORROR -> "/images/horror_genre2.png";
                case ROMANCE -> "/images/romance_genre3.png";
                case MYSTERY -> "/images/mystery_genre4.png";
                case SCIENCE_FICTION -> "/images/science_fiction_genre5.png";
                case FANTASY -> "/images/fantasy_genre6.png";
                case THRILLER -> "/images/thriller_genre7.png";
            };
        }
        // 이미지를 업로드한 경우
        String originalFileName = bookUploadDto.getBookImage().getOriginalFilename();
        String fileExtension = Objects.requireNonNull(originalFileName).substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();

        if (!fileExtension.equals("jpg") && !fileExtension.equals("jpeg") && !fileExtension.equals("png")) {
            throw new IllegalArgumentException("확장자 타입은 jpg, jpeg, png 만 가능합니다.");
        }

        UUID uuid = UUID.randomUUID();
        String imageFileName = uuid + "_" + originalFileName;
        String thumbnailImageFileName = "thumb_" + imageFileName;

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);
        File originalImageFile = new File(uploadFolder + imageFileName);

        try {
            // 원본 이미지 저장
            Files.write(imageFilePath, bookUploadDto.getBookImage().getBytes());

            // 썸네일 생성
            Thumbnails.of(originalImageFile)
                    .size(150, 120)
                    .keepAspectRatio(false)
                    .toFile(new File(uploadFolder + thumbnailImageFileName));

            // 원본 이미지 파일 삭제
            boolean delete = originalImageFile.delete();
            if (delete) {
                log.info("원본 이미지 파일 삭제 성공");
            } else {
                log.info("원본 이미지 파일 삭제 실패");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return thumbnailImageFileName;
    }

    // 일반 사용자가 책 정보를 가져올 때
    @Transactional
    public BookDto getBook(Long bookId, Long loggedId, String order, Pageable pageable) {
        BookDto bookDto = getBookDetails(bookId, order, pageable);
        int likeCount = likeCount(bookId);
        boolean likeState = isLikedByUser(bookId, loggedId);
        int subscribeCount = subscribeService.subscribeCount(bookId);
        boolean subscribeState = subscribeService.isSubscribed(bookId, loggedId);

        List<Object> chapterIdList = chapterService.getChapterList(bookId);

        log.info("getchapteridlist==={}",chapterIdList.toString());

        return bookDto.toBuilder()
                .likeCount(likeCount)
                .likeState(likeState)
                .subscribeCount(subscribeCount)
                .subscribeState(subscribeState)
                .chapterIdList(chapterIdList)
                .build();
    }

    // 작가가 책 정보를 가져올 때
    @Transactional
    public BookDto getBook(Long bookId, String order, Pageable pageable) {
        BookDto bookDto = getBookDetails(bookId, order, pageable);
        int subscribeCount = subscribeService.subscribeCount(bookId);

        return bookDto.toBuilder()
                .subscribeCount(subscribeCount)
                .build();
    }

    private BookDto getBookDetails(Long bookId, String order, Pageable pageable) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );
        Sort sort = Sort.by(Sort.Direction.fromString(order), "createdAt");
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Page<ChapterDto> chapterDtoList = chapterService.getChapterList(bookId, pageable);
        Long totalHits = Optional.ofNullable(chapterRepository.sumHitsByBookId(bookId)).orElse(0L);

        return BookDto.builder()
                .id(bookId)
                .bookName(book.getBookName())
                .writer(book.getMember().getNickname())
                .bookIntro(book.getBookIntro())
                .bookImage(book.getBookImage())
                .bookGenre(book.getBookGenre())
                .ageRating(book.getAgeRating())
                .totalHits(totalHits)
                .chapterList(chapterDtoList)
                .build();
    }

    public Slice<BookListDto> getAllMyBook(Long loggedId, Pageable pageable) {
        return bookRepository.findMyBookList(loggedId, pageable);
    }

    public boolean isMyBook(Long bookId, Long loggedId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );
        return book.getMember().getId().equals(loggedId);
    }

    @Transactional
    public void like(Long bookId, Long loggedId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );
        Member member = memberRepository.findById(loggedId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );
        BookLikes bookLikes = BookLikes.builder()
                .book(book)
                .member(member)
                .build();
        bookLikesRepository.save(bookLikes);
    }

    @Transactional
    public void unlike(Long bookId, Long loggedId) {
        BookLikes bookLikes = bookLikesRepository.findByBookIdAndMemberId(bookId, loggedId);
        if (bookLikes != null) {
            bookLikesRepository.delete(bookLikes);
        } else {
            throw new IllegalArgumentException("해당 사용자가 좋아요를 누르지 않은 책입니다.");
        }
    }

    @Transactional
    public int likeCount(Long bookId) {
        return bookLikesRepository.countByBookId(bookId);
    }

    @Transactional
    public boolean isLikedByUser(Long bookId, Long loggedId) {
        return bookLikesRepository.existsByBookIdAndMemberId(bookId, loggedId);
    }

    @Transactional
    public void deactivateBook(Long bookId, Long loggedId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("해당 책은 존재하지 않습니다."));
        // 해당 책을 작성한 사람인지 예외 처리
        if (book.getMember().getId().equals(loggedId)) {
            book.deactivate();
        } else {
            throw new IllegalArgumentException("해당 책의 작성자가 아닙니다.");
        }
    }

    // 책 정보 수정을 위해 책 정보를 가져옴
    @Transactional
    public BookUploadDto getModifiedBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("해당 책은 존재하지 않습니다."));
        return BookUploadDto.builder()
                .bookName(book.getBookName())
                .bookIntro(book.getBookIntro())
                .bookGenre(book.getBookGenre())
                .ageRating(book.getAgeRating())
                .build();
    }

    @Transactional
    public void updateBook(BookUploadDto bookUploadDto, Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(()
                -> new IllegalArgumentException("해당 책은 존재하지 않습니다."));

        String thumbnailImageFileName = processImageFile(bookUploadDto);
        book.update(bookUploadDto.getBookName(),
                thumbnailImageFileName,
                bookUploadDto.getBookIntro(),
                bookUploadDto.getBookGenre(),
                bookUploadDto.getAgeRating());
        bookRepository.save(book);
    }
}
