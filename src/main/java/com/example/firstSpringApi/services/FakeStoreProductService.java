package com.example.firstSpringApi.services;

import com.example.firstSpringApi.dtos.FakeStoreProductDto;
import com.example.firstSpringApi.models.Category;
import com.example.firstSpringApi.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class FakeStoreProductService implements ProductService{

    private RestTemplateBuilder restTemplateBuilder;
    private RestTemplate restTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplate;
        this.restTemplateBuilder = restTemplateBuilder;
    }

    private Product convertFakeStoreDtoToProduct(FakeStoreProductDto dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setTitle(dto.getTitle());
        product.setPrice(dto.getPrice());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        Category category = new Category();
        category.setDesc(dto.getCategory());
        product.setCategory(category);
        return product;
    }

    @Override
    public Product getProductById(Long id) {
        //Call Fakestore API to get the product with the given id.
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForObject("https://fakestoreapi.com/products/"+id,
                                                  FakeStoreProductDto.class);

        //convert dto to product object.
        if(fakeStoreProductDto == null)
            return null;

        return convertFakeStoreDtoToProduct(fakeStoreProductDto);
    }

    @Override
    public List<Product> getAllProducts() {
        //Call Fakestore API to get all products.
        FakeStoreProductDto[] fakeStoreProductDtos =
                restTemplate.getForObject("https://fakestoreapi.com/products",
                FakeStoreProductDto[].class);
        //convert List of Fakestore dto to List of product object.
        List<Product> products = new ArrayList<>();
        if(fakeStoreProductDtos!=null)
        {
//            for(FakeStoreProductDto dto : fakeStoreProductDtos)
//            {
//                products.add(convertFakeStoreDtoToProduct(dto));
//            }
              products = Arrays.stream(fakeStoreProductDtos).map(s->convertFakeStoreDtoToProduct(s)).toList();
        }
        return products;
    }

    @Override
    //put
    public Product replaceProduct(Long id, Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setTitle(product.getTitle());
        fakeStoreProductDto.setImage(product.getImage());
        fakeStoreProductDto.setDescription(product.getDescription());

        RequestCallback requestCallback = restTemplate.httpEntityCallback(fakeStoreProductDto, FakeStoreProductDto.class);
        HttpMessageConverterExtractor<FakeStoreProductDto> responseExtractor = new HttpMessageConverterExtractor<>(FakeStoreProductDto.class, restTemplate.getMessageConverters());
        FakeStoreProductDto response = restTemplate.execute("https://fakestoreapi.com/products/" + id, HttpMethod.POST, requestCallback, responseExtractor);
        return convertFakeStoreDtoToProduct(response);
    }

    @Override
    public Product updateProduct (Long id, Product product)
    {
        RestTemplate restTemplate = restTemplateBuilder.build();
        New
    }
}
