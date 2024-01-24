package com.project.novel.service;

import com.project.novel.dto.*;
import com.project.novel.entity.Book;
import com.project.novel.entity.BookLikes;
import com.project.novel.entity.Member;
import com.project.novel.repository.BookLikesRepository;
import com.project.novel.repository.BookRepository;
import com.project.novel.repository.MemberRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookService {

    @Value("${file.path}")
    private String uploadFolder;
    private final BookRepository bookRepository;
    private final BookLikesRepository bookLikesRepository;
    private final MemberRepository memberRepository;
    private final ChapterService chapterService;
    private final SubscribeService subscribeService;


    public void write(@Valid BookUploadDto bookUploadDto,
                      @AuthenticationPrincipal CustomUserDetails customUserDetails) {

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

    @Transactional
    public BookDto getBook(Long bookId, Long loggedId, String order) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );

        List<ChapterDto> chapterDtoList = chapterService.getAllChapter(bookId, order);
        int likeCount = likeCount(bookId);
        boolean likeState = isLikedByUser(bookId, loggedId);
        int subscribeCount = subscribeService.subscribeCount(bookId);
        boolean subscribeState = subscribeService.isSubscribed(bookId, loggedId);

        return BookDto.builder()
                .id(bookId)
                .bookName(book.getBookName())
                .writer(book.getMember().getNickname())
                .bookIntro(book.getBookIntro())
                .bookImage(book.getBookImage())
                .bookGenre(book.getBookGenre())
                .ageRating(book.getAgeRating())
                .chapterList(chapterDtoList)
                .likeCount(likeCount)
                .likeState(likeState)
                .subscribeCount(subscribeCount)
                .subscribeState(subscribeState)
                .build();
    }

    public BookDto getBook(Long bookId, String order) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 책입니다.")
        );

        List<ChapterDto> chapterDtoList = chapterService.getAllChapter(bookId, order);
        int subscribeCount = subscribeService.subscribeCount(bookId);

        return BookDto.builder()
                .id(bookId)
                .bookName(book.getBookName())
                .writer(book.getMember().getNickname())
                .bookIntro(book.getBookIntro())
                .bookImage(book.getBookImage())
                .bookGenre(book.getBookGenre())
                .ageRating(book.getAgeRating())
                .subscribeCount(subscribeCount)
                .chapterList(chapterDtoList)
                .build();
    }

    public List<BookListDto> getAllMyBook(Long loggedId, String order) {
        return bookRepository.findAllByMemberId(loggedId, order);
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

}
