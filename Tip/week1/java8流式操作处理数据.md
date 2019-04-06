## java8流式操作处理数据

- 收集  collect(Collector c)  

   	 将流转换成其他形式，接受一个Collector接口的实现

  ```java
  //示例 

    /**
          List alist = Lists.newArrayList();
          alist.stream().collect(Collectors.toList());
         
         
         
         <R, A> R collect(Collector<? super T, A, R> collector); //Stream.java
       
     
   
         //Collectors.toList()    接受一个Collector接口的实现
         public static <T>
             Collector<T, ?, List<T>> toList() {
                 return new CollectorImpl<>((Supplier<List<T>>) ArrayList::new, 										List::add,
                                            (left, right) -> { left.addAll(right); 									return left; },
                                            CH_ID);
             }
             
    */ 
 
  
  
  //用list收集
   List<String> list=employees.stream()
                                     .map(Employee::getName)
                                     .collect(Collectors.toList());
          list.forEach(System.out::println);
  
          System.out.println("----------------------------");
  //用Set收集
          Set<String> set=employees.stream()
                                   .map(Employee::getName)
                                   .collect(Collectors.toSet());
          set.forEach(System.out::println);
  
          System.out.println("----------------------------");
  //用给定的集合类去收集 Collectors.toCollection(HashSet::new)  可以自定义
          HashSet<String> hs=employees.stream()
                                      .map(Employee::getName)
                                      .collect(Collectors.toCollection(HashSet::new));
          hs.forEach(System.out::println);
  
          System.out.println("----------------------------");
  
          //总和
          Long count=employees.stream()
                              .collect(Collectors.counting());
          System.out.println(count);
  
          //平均值
          Double avg=employees.stream()
                              .collect(Collectors.averagingDouble(Employee::getSalary));
          System.out.println(avg);
  
          //总和
          Double sum=employees.stream()
                              .collect(Collectors.summingDouble(Employee::getSalary));
          System.out.println(sum);
  
          //最大值   传入一个比较器为最大值定义规则
          Optional<Employee> max=employees.stream()
                                          .collect(Collectors.maxBy((e1,e2)->Double.compare(e1.getSalary(), e2.getSalary())));
          System.out.println(max.get());
  
          //最小值  传入一个比较器为最小值定义规则
          Optional<Double> min=employees.stream()
                                        .map(Employee::getSalary)
                                        .collect(Collectors.minBy(Double::compare));
          System.out.println(min.get());
  
          System.out.println("----------------------------");
  
          //分组
          Map<Status,List<Employee>> map=employees.stream()
                                                  .collect(Collectors.groupingBy(Employee::getStatus));
          System.out.println(map);//{FREE=[Employee [name=张三, age=18, salary=9999.99, Status=FREE], Employee [name=赵六, age=36, salary=6666.66, Status=FREE]], VOCATION=[Employee [name=王五, age=26, salary=3333.33, Status=VOCATION]], BUSY=[Employee [name=李四, age=58, salary=5555.55, Status=BUSY], Employee [name=田七, age=12, salary=8888.88, Status=BUSY]]}
  
          //多级分组
          Map<Status,Map<String,List<Employee>>> map2=employees.stream()
                                                              .collect( Collectors.groupingBy( 														Employee::getStatus,                       																																Collectors.groupingBy((e)->{
                                                                  if(e.getAge()<=35){
                                                                      return "青年";
                                                                  }else if(e.getAge()<=50){
                                                                      return "中年";
                                                                  }else{
                                                                      return "老年";
                                                                  }
                                                              }) ) );
          System.out.println(map2);//{FREE={青年=[Employee [name=张三, age=18, salary=9999.99, Status=FREE]], 中年=[Employee [name=赵六, age=36, salary=6666.66, Status=FREE]]}, VOCATION={青年=[Employee [name=王五, age=26, salary=3333.33, Status=VOCATION]]}, BUSY={青年=[Employee [name=田七, age=12, salary=8888.88, Status=BUSY]], 老年=[Employee [name=李四, age=58, salary=5555.55, Status=BUSY]]}}
  
          //分区 按照true 与false
          Map<Boolean,List<Employee>> map3=employees.stream()
                                                   .collect(Collectors.partitioningBy((e)->e.getSalary()>8000));
          System.out.println(map3);//{false=[Employee [name=李四, age=58, salary=5555.55, Status=BUSY], Employee [name=王五, age=26, salary=3333.33, Status=VOCATION], Employee [name=赵六, age=36, salary=6666.66, Status=FREE]], true=[Employee [name=张三, age=18, salary=9999.99, Status=FREE], Employee [name=田七, age=12, salary=8888.88, Status=BUSY]]}
  
          System.out.println("--------------------------------");
  
          DoubleSummaryStatistics dss=employees.stream()
                                               .collect(Collectors.summarizingDouble(Employee::getSalary));
          System.out.println(dss.getSum());
          System.out.println(dss.getAverage());
          System.out.println(dss.getMax());
  
          System.out.println("--------------------------------");
          String strr=employees.stream()
                               .map(Employee::getName)
                               .collect(Collectors.joining(","));
          System.out.println(strr);//张三，李四，王五，赵六，田七
       }
  --------------------- 
  作者：商角徵羽宫 
  来源：CSDN 
  原文：https://blog.csdn.net/zxm1306192988/article/details/73744378 
  版权声明：本文为博主原创文章，转载请附上博文链接！
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  ```

  

  









 