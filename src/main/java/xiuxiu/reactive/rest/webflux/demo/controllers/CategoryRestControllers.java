package xiuxiu.reactive.rest.webflux.demo.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import xiuxiu.reactive.rest.webflux.demo.domain.Category;
import xiuxiu.reactive.rest.webflux.demo.repository.CategoryRepository;

import java.util.Objects;

@RestController
public class CategoryRestControllers {

    private final CategoryRepository categoryRepository;

    public CategoryRestControllers(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/api/v1/category")
    Flux<Category> getList(){
        return categoryRepository.findAll();
    }

    @GetMapping("/api/v1/category/{id}")
    Mono<Category> getId(@PathVariable String id){
        return categoryRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/category")
    Mono<Void> create(@RequestBody Publisher<Category> categoryPublisher){
        return categoryRepository.saveAll(categoryPublisher).then();
    }

    @PutMapping("/api/v1/category/{id}")
    Mono<Category> updateCategoryById(@PathVariable String id,@RequestBody Category category){
        category.setId(id);
        return categoryRepository.save(category);
    }

    @PatchMapping("/api/v1/category/{id}")
    Mono<Category> updateCategory(@PathVariable String id,@RequestBody Category category){

        Category foundCategory = categoryRepository.findById(id).block();

        if (!Objects.equals(foundCategory.getDescription(), category.getDescription())){
            foundCategory.setDescription(category.getDescription());
            return categoryRepository.save(category);
        }

        return Mono.just(foundCategory);
    }
}
