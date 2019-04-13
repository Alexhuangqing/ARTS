## 服务发现，eureka客户端

- ### 单词

  - tenets   n. 原理，原则（tenet的复数）；信条	

  - propagate   vt. 传播；传送；繁殖；宣传vi. 繁殖；增殖

    ​		



​	服务发现是微服务基础架构中的关键原则。尝试操作每一个客户端的文件配置，和一些约定的形式做起来很困难，而且也是非常脆弱的。Eureka  是 指Netflix Service Discovery Server与 Client 的统称。server端可以配置并部署为高可用集群，集群中每一个Eureka  server可以复制注册在自己server上的微服务状态到其他的server节点上。

- ##### 11.1怎样去获取客户端

  ​	使用group：org.springframework.cloud  与 artifact  ：spring-cloud-starter-eureka （MAVEN的gav配置）将Eureka Client 引入到你的工程中。使用当前Spring Cloud Release Train.构建你的系统，请到[Spring Cloud Project page](https://projects.spring.io/spring-cloud/) 获取详情

- ##### 11.2 注册Eureka

  ​	当一个客户端注册到Eureka ，它会提供它自己的元信息，例如：host  ，port ，健康检查的url，主页信息等等。Eureka  接受从属于一个服务实例的心跳包，如果心跳间隔超过配置的时间间隔，该实例信息将从实例表中移除。

  ~~~java
  @Configuration
  @ComponentScan
  @EnableAutoConfiguration
  @EnableEurekaClient
  @RestController
  public class Application {
  
      @RequestMapping("/")
      public String home() {
          return "Hello world";
      }
  
      public static void main(String[] args) {
          new SpringApplicationBuilder(Application.class).web(true).run(args);
      }
  
  }
  ~~~

  ​	在这个例子中，我们明确使用@`EnableEurekaClient`  ，但是仅仅使Eureka 可用，你也可以使用 @EnableDiscoveryClient ，配置中要求定位到 Eureka server 。

  **application.yml** 

  ~~~java
  eureka:
    client:
      serviceUrl:
        defaultZone: http://localhost:8761/eureka/
  ~~~

  ​	"defaultZone"  不是表示一个优先级，表示的是一个魔数，为任何客户端，提供一个回调的url。

  ​	默认的应用名（也称作service ID ），虚拟host,非安全的端口，是指，${spring.application.name} ，${spring.application.name}， ${server.port} 

  这些都是来自于Environment 配置。

  ​	@EnableEurekaClient使 app  既是Eureka  “实例”角色（将自己注册）有是“client ”（通过定位到其他服务器上查询注册列表）。“实例”行为是有eureka.instance.* 这些配置属性驱动的。如果能确保app有一个spring.application.name （这是默认得的Eureka  service ID与VIP ），那么默认的配置也能运行。

  ​	访问  [EurekaInstanceConfigBean](https://github.com/spring-cloud/spring-cloud-netflix/tree/master/spring-cloud-netflix-eureka-client/src/main/java/org/springframework/cloud/netflix/eureka/EurekaInstanceConfigBean.java)  和  [EurekaClientConfigBean](https://github.com/spring-cloud/spring-cloud-netflix/tree/master/spring-cloud-netflix-eureka-client/src/main/java/org/springframework/cloud/netflix/eureka/EurekaClientConfigBean.java)  获取更多的配置选项细节。

 - ##### 11.3   Eureka Server验证

  		如果eureka.client.serviceUrl.defaultZone 配置的URLs（例如：<http://user:password@localhost:8761/eureka> ） 中任意一个配置在client 端，基于HTTP 的验证会自动配置加载到eureka client ，为了更复杂的需要，也可以创建一个DiscoveryClientOptionalArgs 的bean注入到ClientFilter 实例中，`ClientFilter`  实例中每一个bean都会在client  访问  server 时被调用。

  		由于Eureka  限制，不可能支持每个服务器的基本验证，只有第一次被发现的设置才会被使用。
 - ##### 11.4  状态页与健康指示器

  		代表性Eureka  实例的状态页与健康指示器默认为“/info ”，“/health ”，他们也是Spring Boot Actuator  默认的端点地址。你需要通过一些配置，改变这些内容

  ~~~java
  eureka:
    instance:
      statusPageUrlPath: ${management.context-path}/info
      healthCheckUrlPath: ${management.context-path}/health
  ~~~

  ​	这些链接会展示在元信息中，会被客户端所消费，这些链接在一些场景下决定是否发送请求到你的应用，因此，如果这些信息是准确的，那将是非常有用的。
 - #####11.5  注册为一个安全的应用

   ​	如果你的app想通过HTTPS  联系，你可以设置在配置文件EurekaInstanceConfig 中两个指标，即eureka.instance.[nonSecurePortEnabled,securePortEnabled]=[false,true] ，这将确保Eureka  发布的实例信息展示在一个安全的社区环境中。Spring Cloud 的`DiscoveryClient`  端会返回一个以`https`  开头的定位URI  对于每一个服务配置而言，并且，Eureka  本身的实例信息也会有一个安全的健康检查URL 。

   ​	由于Eureka  内部的工作机制，他将暴露一个不安全的status  与homepageURL,除非你能够复写他们。你可以用占位符去配置eureka 实例urls

   **application.yml.**  

   ~~~java
   eureka:
     instance:
       statusPageUrl: https://${eureka.hostname}/info
       healthCheckUrl: https://${eureka.hostname}/health
       homePageUrl: https://${eureka.hostname}/
   ~~~

   

   

 - #####11.6 Eureka的健康检查

   ​	默认的，Eureka  使用客户端心跳来决定一个客户端是否可用。除非明确指出，否则Discovery Client 不会传递当前的检测状态给每一个Actuator 应用。这将意味着在成功完成注册Eureka  后，总是会声明应用是‘UP’状态。可以通过保证健康检查来改变着一个行为，健康检查会传播应用的状态给Eureka 。因此，每一个其他应用将不会在不是'UP'状态下，进行通信。

   **application.yml.**  

   ~~~java
   eureka:
     client:
       healthcheck:
         enabled: true
   ~~~

   ​	eureka.client.healthcheck.enabled=true 应该在application.yml 中设置，在bootstrap.yml 设置值会造成不可预知的副作用，例如以错误的状态注册到eureka  。

   ​	如果你想更多的控制到健康检查中，你可能要考虑实现一个handler:com.netflix.appinfo.HealthCheckHandler 。

 - #####11.7 实例与客户端的Eureka 元数据

 - #####11.8 使用EurekaClient

 - #####11.9 原生Netflix EurekaClient的替代物

 - #####11.10 为什么注册一个服务如此之慢

 - #####11.11  Zones