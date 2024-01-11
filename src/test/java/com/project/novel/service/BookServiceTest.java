package com.project.novel.service;

import com.project.novel.repository.MemberRepository;
import com.project.novel.repository.book.BookRepository;
import com.project.novel.repository.ChapterRepository;
import com.project.novel.repository.SubscribeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BookServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private SubscribeRepository subscribeRepository;

    @Autowired
    private BookService bookService;

    private static boolean check = false;
//
//    @BeforeEach
//    public void init() {
//        if(!check) {
//            Member member1 = new Member("userC", "1234", "12@12", 12, "1234", 123, false);
//            Member member2 = new Member("userB", "1234", "12@12", 12, "1234", 123, false);
//            memberRepository.save(member2);
//            memberRepository.save(member1);
//            Member member = new Member("userA", "1234", "12@12", 12, "1234", 123, false);
//            memberRepository.save(member);
//            Book book = new Book("왕좌의 게임", "진모가 왕이 되었다.",  member);
//            bookRepository.save(book);
//            Book book1 = new Book("권준의 게임", "권준이가 왕이 되었다.",  member);
//            bookRepository.save(book1);
//
//            Chapter chapter = new Chapter("chapter1", "이제는 황제가 되었다.", 14L, 12L, 200, book);
//            chapterRepository.save(chapter);
//            Chapter chapter1 = new Chapter("chapter1", "이제는 황제가 되었다.", 11L, 11L, 200, book1);
//            chapterRepository.save(chapter1);
//            Chapter chapter3 = new Chapter("chapter1", "이제는 황제가 되었다.", 11L, 17L, 200, book);
//            chapterRepository.save(chapter3);
//            Chapter chapter4 = new Chapter("chapter1", "이제는 황제가 되었다.", 10L, 14L, 200, book1);
//            chapterRepository.save(chapter4);
//
//            Subscribe subscribe1 = new Subscribe(book, member);
//            Subscribe subscribe2 = new Subscribe(book1, member1);
//            Subscribe subscribe3 = new Subscribe(book1, member2);
//            subscribeRepository.save(subscribe1);
//            subscribeRepository.save(subscribe2);
//            subscribeRepository.save(subscribe3);
//
//            check = true;
//        }
//    }
//
//    @Test
//    @DisplayName("구독자 순으로 bookList정렬하기")
//    public void subscribesSort() {
//
//        List<BookDto> bookDto = bookService.findAllBook("subscribes");
//
//
//        Assertions.assertThat(bookDto.get(0).getSubscribes() >= bookDto.get(1).getSubscribes()).isTrue();
//        Assertions.assertThat(bookDto.size()).isEqualTo(2);
//    }
//
//    @Test
//    @DisplayName("좋아요 순으로 bookList정렬하기")
//    void likesSort(){
//        List<BookDto> bookDto = bookService.findAllBook("likes");
//
//        Assertions.assertThat(bookDto.get(0).getLikes() >= bookDto.get(1).getLikes()).isTrue();
//        Assertions.assertThat(bookDto.size()).isEqualTo(2);
//    }
//
//    @Test
//    @DisplayName("조회 순으로 bookList정렬하기")
//    public void hitSort() {
//
//        List<BookDto> bookDto = bookService.findAllBook("hit");
//
//        Assertions.assertThat(bookDto.get(0).getHit() >= bookDto.get(1).getHit()).isTrue();
//        Assertions.assertThat(bookDto.size()).isEqualTo(2);
//    }
//
//    @Test
//    @DisplayName("수정 날짜 순으로 bookList정렬하기")
//    public void lastModifiedDate(){
//        List<BookDto> bookDto = bookService.findAllBook("modifiedDate");
//
//        for (BookDto dto : bookDto) {
//            System.out.println("dto.getModifiedDate() = " + dto.getModifiedDate());
//        }
//
//        Assertions.assertThat(bookDto.get(0).getModifiedDate().isAfter(bookDto.get(1).getModifiedDate())).isTrue();
//        Assertions.assertThat(bookDto.size()).isEqualTo(2);
//    }
}