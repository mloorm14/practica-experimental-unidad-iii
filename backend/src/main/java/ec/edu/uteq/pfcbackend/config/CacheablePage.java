package ec.edu.uteq.pfcbackend.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

// PageImpl (y su Pageable/Sort internos) no tienen constructores Jackson-friendly;
// esta subclase permite que GenericJackson2JsonRedisSerializer la reconstruya al leer desde Redis.
// Como efecto colateral, "pageable" y "sort" dejan de aparecer en el JSON de respuesta del endpoint
// (quedan number/size/totalElements/totalPages/first/last, que ya cubren la info de paginacion).
@JsonIgnoreProperties(ignoreUnknown = true)
public class CacheablePage<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CacheablePage(@JsonProperty("content") List<T> content,
                          @JsonProperty("number") int number,
                          @JsonProperty("size") int size,
                          @JsonProperty("totalElements") long totalElements) {
        super(content, PageRequest.of(number, size == 0 ? 1 : size), totalElements);
    }

    @Override
    @JsonIgnore
    public Pageable getPageable() {
        return super.getPageable();
    }

    @Override
    @JsonIgnore
    public Sort getSort() {
        return super.getSort();
    }
}
