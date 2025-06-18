# jackson
## reference
- https://chatgpt.com/c/0f8b8a16-cab5-4fbb-8f0e-1c7949dd97f4 - anno 1
- https://chatgpt.com/c/cbe3f6c3-c238-4db8-99e5-a0c9c9ddffec - anno 2
- https://chatgpt.com/c/4334405c-e8c0-4e88-a138-a53b14ae52b5 - object mapper
- https://www.baeldung.com/jackson
--- 
- Json , JsonStr , Object , JsonNode(asText(), readTree())
- Objectmapper / Xmlmapper API
- @ResquestBody and @responseBody behind the scene perform S and D using OM.
-  Advance Section pending : https://www.baeldung.com/jackson

---
## Common task
1. Serialize and Deserialize 
   - Class, generic-Class
   - interface and abstractClass : Does not work.
2. property order, alias, root
3. Type/property 
   - @JsonAlias
   - @JsonUnwrapped - performs flattening. {a,{b,c}} === {a,b,c}
   - @JsonSetter is an alternative to @JsonProperty that marks the method as a setter method.
   - @JsonIgnore, @JsonIgnoreType, @JsonFilter - conditionally ignore.
   - @JsonInclude(Include.NOT_NULL) , @JsonIncludeProperties class
   - globally set : om.setSerializationInclusion(Include.NON_NULL);
4. formatting : date, time, number, etc : 
   - @DateTimeFormat(fromSB)
   - om.setDateFormat(new SimpleDateFormat(" "))
5. handle collection.
6. @JsonCreator(apply on constructor/method to create/return object)
   tune the constructor/factory used in deserialization.

```
    - String jsonCarArray ="[{ \"color\" : \"Black\", \"type\" : \"BMW\" }, { \"color\" : \"Red\", \"type\" : \"FIAT\" }]";
      List<Car> listCar = objectMapper.readValue(jsonCarArray, new TypeReference<List<Car>>(){});
      
    - String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
      Map<String, Object> map = objectMapper.readValue(json, new TypeReference<Map<String,Object>>(){});
```
5. handle raw json
6. handle Enums - @JsonValue for S, @JsonCreator for D 
    ```
   // Default
   public enum Status {SUCCESS,FAILURE,PENDING}
      String json = mapper.writeValueAsString(Status.SUCCESS); //  "\"SUCCESS\""
      Status status = mapper.readValue(json, Status.class); // SUCCESS
   
   // @JsonValue for S, @JsonCreator for D 
   public enum Status {String v;SUCCESS("S"),FAILURE("F"),PENDING("P");
    Status(string v){} 
    @JsonValue getValue(v)
    @JsonCreator m(v){...}
   }
     String json = mapper.writeValueAsString(Status.SUCCESS); //  "\"S\""
     Status status = mapper.readValue(json, Status.class); // S
   ```
7. Don't use Optional<T> as json property.
   - Add Dependencies : jackson-datatype-jdk8.
   - objectMapper.registerModule(new Jdk8Module());
   - ```
     @JsonInclude(JsonInclude.Include.NON_ABSENT) // Optional fields are included if present
     public class Event {
        private String name;
        private Optional<String> description;
     }
     ```
8. handle visibilty: @JsonAutoDetect(fieldVisibility = Visibility.ANY) // Private feild. etc.
9. 

---

## Advance task
1. inject
    - @JacksonInject indicates that a property will get its value from the injection and not from the JSON data.
   ```
      InjectableValues inject = new InjectableValues.Std().addValue(int.class, 1);
      BeanWithInject bean = new ObjectMapper()
                            .reader(inject)
                            .forType(BeanWithInject.class)
                            .readValue(json);
   ```
2.

---

## Objectmapper
- Builder pattern

### Serialize
- writeValue(obj) or 
- writeValueAsString(obj)
-  Apply Filter :
  - @JsonFilter("Filter-1") class
  - om.writer(SimpleFilterProvider p).writeValue(obj) //p.addFilter("Filter-1", filterObject eg:SimpleBeanPropertyFilter)


### De-serialize
- readvalue (json, TypeReference<T>)
- readerFor(T).readValue(obj)

### Config
- unknown/incomplete
    - FAIL_ON_UNKNOWN_PROPERTIES , @JsonIgnoreProperties(ignoreUnknown = true) MyClass
    - FAIL_ON_NULL_FOR_PRIMITIVES : unmarshalling an incomplete JSON :  @JsonProperty(required = false.True)

- mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE);
- WRITE_DATES_AS_TIMESTAMPS,
- DEFAULT_VIEW_INCLUSION,
- INDENT_OUTPUT 
- OM >> register module >> add custom S and D
- OM.configure(k,v)
- on DTO >> Apply annotation
  - https://chatgpt.com/c/0f8b8a16-cab5-4fbb-8f0e-1c7949dd97f4
  - https://chatgpt.com/c/cbe3f6c3-c238-4db8-99e5-a0c9c9ddffec
- spring.jackson.<category_name>.<feature_name>=true,false
    - spring.jackson.serialization.indent_output=true
    - spring.jackson.deserialization.fail-on-unknown-properties=false
    - spring.jackson.mapper.auto-detect-fields=true // @JsonAutoDetect
    - spring.jackson.parser.allow-comments=true
    - spring.jackson.generator.escape-non-ascii=true
  ```
        return new ObjectMapper()
            .enable(SerializationFeature.INDENT_OUTPUT)             // Enable pretty printing
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES) // Ignore unknown properties
            .enable(MapperFeature.AUTO_DETECT_FIELDS)              // Auto-detect fields
            .enable(JsonParser.Feature.ALLOW_COMMENTS)             // Allow comments in JSON
            .enable(JsonGenerator.Feature.ESCAPE_NON_ASCII); 
  ```
---
- UnrecognizedPropertyException : objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
- extends Std/JsonSerializer<Car> --> serialize(T object, JsonGenerator jsonGenerator, SerializerProvider serializer) {...}
-  extends Std/JsonDeserializer<Car>  --> T deserialize(JsonParser parser, DeserializationContext deserializer) {...}
- @JsonCreator : Apply on contructor (@jsonproperty on arg). that constructor is used while unmarshalling
- @JsonDeserialize(as = Cat.class) Animal a ; // Animal is Interface.
- @JsonFormat : specifies a format when serializing Date/Time values
  - usally used for java,util.Date, Double price (patter="#0.00")
  - @JsonFormat(shape = JsonFormat.Shape.String, pattern="") Date // 
  - @JsonFormat(shape = JsonFormat.Shape.NUMBER) Date //Timeinmilliseconds
  - @JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES) class  // IMP




