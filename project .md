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
after that making jwt fileter in security

now role based authorization

like this     @PreAuthorize("hasRole('ADMIN')")


now enable this we must enable method security from security , by annotation


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
in db we dont nee3d ROLES_


FOR USing any roles
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")


NOW it is important to know that now excetpion handler not able to handle exception at filter layer

exceptioun like 



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        try {
            String username = null;
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username =  jwtService.extractUsername(token);
            }
            if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        }
        catch (Exception e) {
           generateResponseError(response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void generateResponseError(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        Object body =   GenericResponse.builder()
                .status("failed")
                .message(e.getMessage())
                .responseStatus(HttpStatus.UNAUTHORIZED)
                .build().create().getBody();

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));

    }
}

    private Claims extractAllClaims(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(decryptKey(secretKey))
                    .build().parseSignedClaims(token).getPayload();
        return claims;
        }
        catch (ExpiredJwtException e) {
            throw new JwtTokenExpiredException("Token is Expired");
        }
        catch (JwtException e) {
            throw new JwtTokenExpiredException("Invalid Jwt Token");
        }
        catch (Exception e) {
            throw e;
        }

    }

now making uuser response , we will not keep password in that, 
also making status dto, rest evverythign same likie in user dto


now we can make this 
   public static User getLoggedInUser(){
        CustomUserDetails logUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return logUser.getUser();
    }

now if we type cast here in user response rather doing model mapping is better 

If you haven't added any specific role-based access control (such as role annotations like @PreAuthorize, @Secured, or other security mechanisms) to your @GetMapping("/profile") method, then yes, the endpoint will be accessible to all users
















log details-> of line -- > 
authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
values=>
this = {JwtFilter@13093} 
request = {HeaderWriterFilter$HeaderWriterRequest@15150} 
response = {HeaderWriterFilter$HeaderWriterResponse@15151} 
filterChain = {FilterChainProxy$VirtualFilterChain@15152} 
authHeader = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJpZCI6MSwibmFtZSI6IkFETUlOIn1dLCJpZCI6MjUsInN0YXR1cyI6ZmFsc2UsInN1YiI6ImFkbWluQGFiLmMiLCJpYXQiOjE3NDAxMTYyMDgsImV4cCI6MTc0MDIwMjYwOH0.DpbUreQaVXqcAncvgAOR-HqVo42kerk4FQG0NBB4Amg"
username = "admin@ab.c"
token = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbeyJpZCI6MSwibmFtZSI6IkFETUlOIn1dLCJpZCI6MjUsInN0YXR1cyI6ZmFsc2UsInN1YiI6ImFkbWluQGFiLmMiLCJpYXQiOjE3NDAxMTYyMDgsImV4cCI6MTc0MDIwMjYwOH0.DpbUreQaVXqcAncvgAOR-HqVo42kerk4FQG0NBB4Amg"
userDetails = {CustomUserDetails@15155} 
authentication = {UsernamePasswordAuthenticationToken@15156} "UsernamePasswordAuthenticationToken [Principal=com.pranjal.config.security.CustomUserDetails@4394d549, Credentials=[PROTECTED], Authenticated=true, Details=null, Granted Authorities=[ROLE_ADMIN]]"


 of line -- > 
SecurityContextHolder.getContext().setAuthentication(authentication);

 values-> 
 authentication = {UsernamePasswordAuthenticationToken@15156} "UsernamePasswordAuthenticationToken [Principal=com.pranjal.config.security.CustomUserDetails@4394d549, Credentials=[PROTECTED], Authenticated=true, Details=WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null], Granted Authorities=[ROLE_ADMIN]]"
 principal = {CustomUserDetails@15155} 
 credentials = null
 authorities = {Collections$UnmodifiableRandomAccessList@15161}  size = 1
  0 = {SimpleGrantedAuthority@15166} "ROLE_ADMIN"
   role = "ROLE_ADMIN"
 details = {WebAuthenticationDetails@15162} "WebAuthenticationDetails [RemoteIpAddress=0:0:0:0:0:0:0:1, SessionId=null]"
  remoteAddress = "0:0:0:0:0:0:0:1"
  sessionId = null
 authenticated = true

 of line -> 
 filterChain.doFilter(request, response);
 values ->
 this = {JwtFilter@13093} 
request = {HeaderWriterFilter$HeaderWriterRequest@15150} 
 response = {HeaderWriterFilter$HeaderWriterResponse@15151} 
 request = {StrictHttpFirewall$StrictFirewalledRequest@15170} "FirewalledRequest[ org.apache.catalina.connector.RequestFacade@716cb2c8]"
  this$0 = {StrictHttpFirewall@15177} 
  request = {RequestFacade@15178} 
response = {HeaderWriterFilter$HeaderWriterResponse@15151} 
 response = {DisableEncodeUrlFilter$DisableEncodeUrlResponseWrapper@15180} 
 request = {StrictHttpFirewall$StrictFirewalledRequest@15170} "FirewalledRequest[ org.apache.catalina.connector.RequestFacade@716cb2c8]"
 this$0 = {HeaderWriterFilter@15179} 
 disableOnCommitted = false
 contentLength = 0
 contentWritten = 0
filterChain = {FilterChainProxy$VirtualFilterChain@15152} 
 originalChain = {FilterChainProxy$lambda@15182} 
 additionalFilters = {ArrayList@15183}  size = 13
 size = 13
 currentPosition = 6


 from function 

     @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String authHeader = request.getHeader("Authorization");
            String username = null;
            String token = null;
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                username =  jwtService.extractUsername(token);
            }
            if (username!=null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        }
        catch (Exception e) {
           generateResponseError(response, e);
            return;
        }

        filterChain.doFilter(request, response);
    }
some my own findings from this
* got correct username
* bebugging not going in catch












this is secuirty config

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
    public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }





    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
//		provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**", "/api/v1/home/**")
                        .permitAll().anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        ;

        return http.build();
    }
}

Signature mismatch & Expired token already handled with messages

now making PasswordChangeRequest in Dto

now setting the password Reset Token option in verfiy option

    @GetMapping("/send-email-reset")
    private ResponseEntity<?> sendEmailForPasswordReset() {
        
        return null;
    }

    @GetMapping("/verfy-paswd-link")
    private ResponseEntity<?> verifyPasswordResetLink(){

        return null;
    }

    @GetMapping("/reset-paswd")
    private ResponseEntity<?> resetPassword(){
        
        return null;
    }



     public static User getLoggedInUser(){

        try{
        CustomUserDetails logUser = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return logUser.getUser();

        }catch (Exception e){
            throw e;
        }

    }

    now reset-=apswed -> inhereit from parent (very imp to remember
    =)








now we will implement the searching
    in notes by title,descriptoin, cateogry
    