E Notes Project Real Time Project
=================================
    Technology Stack
        Backend-> Spring Boot Rest, JPA, AOP, Logging, Actuator, Security, OAuth Login, Caching, Junit & Mockito, Sonar Cube, JMeter , Swagger, Schedular, Docker, Github

        Frontend-> Angular / React

        DB -> MySQL

        Tools -> Github, STS, Figma

        Deploy Server -> AWS
            
            
            
            
   AOP: Aspect Orinted Programming
        

before startin our project we hafve to build its er project


now created tables ,

user, category, file ,  notes, role , todo , user , user_role (mapping)


now setting project like industry , 
    makin github setup, 
    creating repos
    branch
        dev  = devloper
        test = tester
        uat  = client
        prod = live

    
    sub-branch for partuclar devloper  in dev
    like nav for navbar in dev
    then we will merge it with dev

    then test will give in test , then client see, then in prod



    now making stadards packages    

    now as this contain overallapping attribute we can create BaseModel then extend the enities with it
    
    then 
    @MappedSuperclass
    public class BaseModel {


    then create different classes, packages , ...


    create controller like 
    @RestController
@RequestMapping("/api/v1/category")
public class CategoryController {

    @PostMapping("/save-category")
    public ResponseEntity<?> saveCategory(){
        return new ResponseEntity<>("saved", HttpStatus.CREATED);
    }
}






now created tables ,

user, category, file ,  notes, role , todo , user , user_role (mapping)\


after this we will procddding parralellel to it continue the    


now introducing the dto

after this for easier mapping created bean of ModelMapper in config 


apart from dto create response of repoecive class also#

here we dont delete user data directly, it's not recommended so delete with so we have isDelete , os make delete in persepective o  f user

 @DeleteMapping("/{id}")
   @GetMapping("/{id}")

   even these both can have same address (but have different method)


for exception we can create exception class too

@ControllerAdvice can attached above exception class



@lf4j for logging infor error hadnling

now doing validation part

<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-validation -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
    <version>3.4.2</version>
</dependency>


like 
public class CategoryDto {
    private Integer id;
    @NotBlank
    @Min(value = 1)
    @Max(value = 50)
    private String name;

    @NotBlank
    @Min(value = 10)
    @Max(value = 250)
    private String description;

    @NotNull
    private Boolean isActive;


now to activate we have to do @Valid in controller


@PostMapping("/save")
    public ResponseEntity<?> saveCategory(@Valid @RequestBody CategoryDto category) {


@Size(min = 1, max = 50, message = "Name must be between 1 and 50 characters")

size for string, 
min, max for numbers



     DtoValidationException extends RuntimeException   error may happen during runtime so unchecked exception 


    but instead of these all we should do Validation Class, and there check , where it will clean and industry standards

    now validation should not in controller layer, instead in implementation layer , with util package validation component (class)

    