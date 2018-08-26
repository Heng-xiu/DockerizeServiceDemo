package xiuxiu.reactive.rest.webflux.demo.controllers;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xiuxiu.reactive.rest.webflux.demo.domain.Category;
import xiuxiu.reactive.rest.webflux.demo.repository.CategoryRepository;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class CategoryRestControllersTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryRestControllers categoryRestControllers;

    @Before
    public void setUp() throws Exception {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryRestControllers = new CategoryRestControllers(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryRestControllers).build();
    }

    @Test
    public void getList() throws Exception {
        given(categoryRepository.findAll())
                .willReturn(
                        Flux.just(
                                Category.builder().description("Cat").build(),
                                Category.builder().description("Dog").build()
                        )
                );

        webTestClient.get()
                .uri("/api/v1/category")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);

    }

    @Test
    public void getId() throws Exception {
        given(categoryRepository.findById("someId"))
                .willReturn(
                        Mono.just(Category.builder().description("Cat").build())
                );

        webTestClient.get()
                .uri("/api/v1/category/someId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createCategoryAll() throws Exception{
        given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> catToSaveMono = Mono.just(Category.builder().description("some cats").build());

        webTestClient.post()
                .uri("/api/v1/category")
                .body(catToSaveMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateCategoryByID() throws Exception{
        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("some cats").build());

        webTestClient.put()
                .uri("/api/v1/category/1122334455")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

    }

    @Test
    public void patchCategoryByID() throws Exception{

        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().build()));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("DogDog").build());

        webTestClient.patch()
                .uri("/api/v1/category/1122334455")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository).save(any());
    }

    @Test
    public void patchCategoryByIDWithoutChange() throws Exception{

        given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(Category.builder().description("some Cats").build()));

        given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().build()));

        Mono<Category> catToUpdateMono = Mono.just(Category.builder().description("some Cats").build());

        webTestClient.patch()
                .uri("/api/v1/category/1122334455")
                .body(catToUpdateMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        verify(categoryRepository, never()).save(any());

    }
}