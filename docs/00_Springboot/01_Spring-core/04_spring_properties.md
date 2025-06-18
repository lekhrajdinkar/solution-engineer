- https://chat.openai.com/c/d75560a0-1c06-4195-80fc-563ec8449bc5
- https://www.baeldung.com/configuration-properties-in-spring-boot

---
# Application properties

## 1. Register
### Automatically registered files:
- application.property - default file
- application-<profile/env>.properties

### Register custom properties-file
- use `@propertySource / @propertySources`
- keep here - **src/main/resources** : sb has utility method to read from resource folder.
- check : [ReadConfigFromGlobalProperty.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSpring_03_Properties%2Fconfiguration%2FReadConfigFromGlobalProperty.java)[ReadConfigFromGlobalProperty.java](..%2FSpring_03_Properties%2Fconfiguration%2FReadConfigFromGlobalProperty.java)
- can have env/profile specific files too, as shoen below.
```
@Configuration  
@PropertySources(value = {
        @PropertySource(value = "classpath:global-database-${spring.profiles.active}.properties"),
        @PropertySource(value = "classpath:global-rabbit-mq-${spring.profiles.active}.properties")
})
```
- external property files
```
1. JVM ARG :: java -jar my-app.jar --spring.config.location=<path>/application.properties
2. env var :: export SPRING_CONFIG_LOCATION=/config/external/application.properties
3. Command Line Arguments  :: ?
```

## 2. Binding into custome class
- check : [ConfigurationPropertiesByPrefixBean.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSpring_03_Properties%2Fbean%2FConfigurationPropertiesByPrefixBean.java)
- **property** --> sb does **binding** bts --> into **Object** (of ConfigurationPropertiesByPrefixBean class)
- **@ConfigurationProperties**
```
@ConfigurationProperties(prefix = "mail")
@ConfigurationPropertiesScan // register bean
public class ConfigurationPropertiesByPrefixBean
{
    ...
}
```
```
// Using @ConfigurationProperties on a @Bean Method
// pending...
```
- more ways to **register** above class as bean - ConfigurationPropertiesByPrefixBean
  - EnableConfigurationProperties({c1.class, c2.class}) --> enable/register explicitly, may be in Application.java file
  - add @ConfigurationPropertiesScan Apply on class c1/c2 (done above)
  - add @Component
  - add @Configuration

### in-built binding
- property name must match - b/w prop and java class fields.
```
all these works
- mail.hostName
- mail.hostname
- mail.host_name
- mail.host-name
- mail.HOST_NAME
```
```
# prefix = mail

# ------ String  --------
mail.hostname =    mailer@mail.com

# ------ number  --------
mail.port     =    9000

# ------ List / set --------
mail.defaultRecipients[0]=admin@mail.com
mail.defaultRecipients[1]=owner@mail.com

# ------- Object  -------
mail.credentials1.username=john
mail.credentials1.password=password
mail.credentials1.authMethod=SHA1

# ------- Map<String,String>  ---------
mail.additionalHeaders.redelivery=true
mail.additionalHeaders.secure=true

# ------- Map<String,Object>  ---------
mail.map1.credentials1.username=mani
mail.map1.credentials1.password=password1
mail.map1.credentials2.username=lek
mail.map1.credentials2.password=password12

map :
credentials1=object:{mani,password1}
credentials2=object:{lek,password2}

# ------- Map<String,list<Object>>  ---------

# ----- Duration,DataSize ------

```
- check this for more Map binding example:
  - [Prop2Map.java](..%2FSpring_03_Properties%2Fbean%2FProp2Map.java)

### custom binding
- create converter class:
  - 1 annotate with **@ConfigurationPropertiesBinding**
  - 2 implement **Converter<SourceType,TargetType>** --> @override TargetType **convert**(SourceType)
    - [Prop2Map.java](..%2F..%2Fsrc%2Fmain%2Fjava%2Fcom%2Flekhraj%2Fjava%2Fspring%2FSpring_03_Properties%2Fbean%2FProp2Map.java)[CustomConverter_1.java](..%2FSpring_03_Properties%2Fconverter%2FCustomConverter_1.java)
    - string to Credential binding
    - `mail.credentials2 = john2,password2`
  - 3 add **validation** (JSR-380 format), if needed
    ```
    @NotBlank
    @length(min,max)
    @Max(1025)
    @Min(1025)
    @Pattern(regexp = "^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$")
    ```

## 3. Read properties
- `@Autowired Environmnet` env.
- `@value` 
  - hardcode static value
  - SpEL


