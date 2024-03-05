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


now applying auditing

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
    @Max(value = 250
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

    


    we should send data in generic way, genric format


    infact making seprate branch handler for genric response


Now making notes class
in dto for other class table we should take dto of it only 


like @Getter
@Setter
@MappedSuperclass
public class NotesDto {
    private Integer id;
    private String title;
    private String description;
    private CategoryDto category;

    private Integer createdBy;
    private Date createdOn;
    private Integer updateBy;
    private Date updateOn;



    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CategoryDto{
        private Integer id;
        private String name;
    };
}



error solved -> recived error by extended by throwable but finally figured out i should have to do with exception for custom message in custom error 

even for file we have to do according to industry standards
also we have feautre like if string is too long like
java_programming.....pdf
show as java.prog.pdf

we have attributes like random name , original name, show name

for files have apache commons

model mapper between classes maded , entities
object mapper between string and object 




using this repose in download -> return  ResponseEntity.ok().headers(headers).body(downloadFile);
    

    notice in this there is no new,


            NotesResponse notesResponse = NotesResponse.builder().build();;



now implementing git update branch

we will delete data after 30 days, 
    introducing isDeleted variable

    it will soft delete

    similarly making restore option for user



now implementing schheudalr deleting at 7 day period


eg =>

@Component
public class NotesSchedular {

    int i = 0;
    @Scheduled(fixedRate = 1000)
    public void deleteNotesScheduler(){
        System.out.println(i++);
    }
}
1000 means 1 second
also we should do in LocalDate not date 


@Component
public class NotesSchedular {

    @Autowired
    private NotesRepository notesRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteNotesScheduler(){
        LocalDateTime cutoffDate = LocalDateTime.now().minusMinutes(5);
        List<Notes> deleteNotes = notesRepository.findByIsDeletedAndDeletedAtBefore(true, cutoffDate);
        notesRepository.deleteAll(deleteNotes);
    }
}

nnow implementing favourite notes, by making it entity, (bw user and notes)



also in real world making of tables is not done by update instead done spefically by manullay first then we make different entities in backend


now implementing the copy notes


now making todo moudle

for tasks, we have to send mails , for rmeinders
for todo , in progrress, completed
push notification, email reminders


for status we can create intger & link it to numbers


    for dto of todo, instead of orginal intger for status, but we can do static class for this 
    
    in lombok we should separate annonation for inner class,

    and in statusDto, attribute are enum values (status, name)


    we will validate todo ,in validation class 



now implementing user module

making one to many list<role> too

better to setup the AuthController instead of UserController

it is never recommended to do update hibernatre auto update, , industry procedure should we applied and none done

user_roles 
user (one) to roles(many)
table are user , role

now we have to on the turn on html by passing true

        mimeMessageHelper.setText(emailRequest.getMessage(), true);

so to do verfication set isActive variable in user module

to deal with varoiuse account things ,like expire, account, verfication code, etc we can make seprate object enitity AccountStatus

one to one relation with user


the on of  advantage of creating a db is make little lossely, becasue in auto update it makes foregin keky because of cascade constraint it become defcult to update 

now intializing the spring security
adding dependencies & making its file in config.security package


    because now in development hase now will not use passwordEncoder


use of eager like in
without eager here it unable to fetch so ggiving error from spring security
    @OneToMany(cascade = CascadeType.ALL)
    private List<Role> roles;


SO TAckle that do like this
@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles;
    
better to authentication by token method , 

by     @Autowired
    private AuthenticationManager authenticationManager;

    
    authentecate