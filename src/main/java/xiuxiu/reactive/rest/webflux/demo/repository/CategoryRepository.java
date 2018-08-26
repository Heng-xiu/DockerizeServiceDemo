package xiuxiu.reactive.rest.webflux.demo.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import xiuxiu.reactive.rest.webflux.demo.domain.Category;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
