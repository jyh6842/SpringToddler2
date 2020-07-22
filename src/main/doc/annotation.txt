사용자 정의 어노테이션의 정의와 활용
   어노테이션 : java 5.0부터 지원되는 빈번하게 활용되어야하는 다수 클래스의 정보 취득 및
               해당 클래스의 컴파일 타임간 검증, 코드 분석, 문서화에서 활용될 수 있으며,
               컴파일 타임시 무의미하게되는 주석(줄단위, 블럭단위 등)과 달리 런타임시까지 
               존재되어 활용됨. 
      ** 자바 제공 어노테이션(@Override[오버라이딩 메서드], 
                       @Deprecated[사용을 권고하지 않음], 
                       @SuppressWarnig[컴파일러 경고 제거])과 
             사용자 정의 어노테이션으로 분리됨         
1. 사용자 정의 어노테이션 작성 정보

   @Target - annotation을 적용 대상 지정.
                  ElementType.CONSTRUCTOR : 생성자
                  ElementType.FIELD : enum상수를 포함한 전역변수
                  ElementType.LOCAL_VARIABLE : 지역 변수
                  ElementType.METHOD : 메소드
                  ElementType.PACKAGE : 패키지
                  ElementType.PARAMETER : 파라미터
                  ElementType.TYPE : 클래스, 인터페이스, enum
                  
  @Retention - annotation 정보의 유효화 설정
                  RetentionPolicy.SOURCE :  [소스]원본 파일 소스단에서 취득 활용가능한 어노테이션 정의
				  RetentionPolicy.CLASS :   [소스|컴파일]된 바이트코드단에서 취득 활용가능한 어노테이션 정의
                  RetentionPolicy.RUNTIME : [소스|컴파일|실행]시 java reflection을 활용해 취득 활용가능한 
                                                                   어노테이션 정의
 
  @Documented - annotation을 JavaDoc 작성시 포함
 
  @Inherited - 부모 Annotation을 상속받음
  
2. example

   ** 어노테이션 인터페이스 작성시 포함되는 함수들은 해당 어노테이션의 필수 속성으로 동작하며, 필수 속성 해제시
      defualt 키워드를 활용함. (public String value();            <= 필수 입력
                           public String value() default ""; <= 필수 입력 해제)

   2.1 @SampleAnnotation을 정의
   
   		public @interface SampleAnnotation {
        }
       
   2.2 @PlaceSampleAnnotation을 클래스 내 전역변수에만 설정 가능하도록 정의하고, 해당 어노테이션의 취득은
          컴파일된 바이트 코드로부터 취득 활용되도록 정의 
   
       @Target(ElementType.FIELD)
       @Retention(RetentionPolicy.CLASS)
	   public @interface PlaceSampleAnnotation {
	   }
	   
   2.3 @PlaceSampleAnnotation을 type(클래스, 인터페이스, enum)과 해당 클래스내 전역변수, 생성자,
          메서드, 메서드의 파라메터, 메서드 내 지역변수에 설정 가능하도록 정의하고, 해당 어노테이션의 취득은
          컴파일된 바이트 코드로부터 취득 활용되도록 정의 
    
       @Target( { ElementType.TYPE, ElementType.FIELD, ElementType.CONSTRUCTOR,
			  ElementType.METHOD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE })
	   @Retention(RetentionPolicy.CLASS)
	   public @interface PlaceSampleAnnotation {
	   }
   
   2.4 @StringValueAnnotation을 클래스 내 전역변수에만 설정 가능하도록 정의하고
          해당 어노테이션 설정시 문자열값을 설정하도록 하고, 해당 어노테이션의 취득은
          실행시 취득 활용되도록 정의 
   
       @Target(ElementType.FIELD)
       @Retention(RetentionPolicy.RUNTIME)
       @interface StringValueAnnotation {
			public String value();	
	   }
       
          활용1. class UseSample{
          		@StringValueAnnotation(value="test1")
                public String value1;
                
                @StringValueAnnotation("test2")
                public String value2;
                
                @StringValueAnnotation
                public String value3;
            }
            
   2.5 @DefaultAnnotation을 클래스, 인터페이스, enum을 대상으로 적용될수있으며, 해당 어노테이션에
         값 미설정시 default값이 적용되며, 해당 어노테이션의 취득은 실행시 취득 활용되도록 정의 
   
       @Target(ElementType.TYPE)
       @Retention(RetentionPolicy.RUNTIME)
       @interface DefaultAnnotation {
			public String value() default "default값";
	   }
   
          활용1.  @DefaultAnnotation(value="값")
             class UseSample{
          
             }

          활용2.  @DefaultAnnotation("값")
             class UseSample{
          
             }
          활용2.  @DefaultAnnotation   // 값 미설정시 어노테이션 작성시의 default값이 적용됨.
              class UseSample{
           
              }             
            
   2.6 @ValuesAnnotation을 [속성(전역변수)|생성자]에 적용될 수 있으며, [소스|컴파일|실행]시에
          적용되도록하고, 해당 어노테이션 적용시 배열로 설정하도록 지정하고, 해당 어노테이션의 취득은
          실행시 취득 활용되도록 정의 
  		 
       @Target({ElementType.FIELD, ElementType.CONSTRUCTOR })
       @Retention(RetentionPolicy.RUNTIME)
       @interface ValuesAnnotation(
       		public name nameValue();
       		public int[] intValues();
       		public String[] strValues();
       }
            
          활용1.  class UseSample{
          		@ValuesAnnotation(intValues={1,2,3})
          		public int i;
          		
          		@ValuesAnnotation(intValues=1)       // 배열의 형태로 선언되었지만, 한개의 값을 선언할수 있음.
          		public int j;
          		
          		@ValuesAnnotation(name="생성자", intValues={1,2,3,4}, strValues={"a","b","c"})
          		public UseSample(){
          		
          		}
          		
          		or
          		
          		@ValuesAnnotation(intValues=1, strValues="a")
          		public UseSample(){
          		
          		}
             }            
            
   2.7 @ParamAnnotation을 [속성(전역변수)|생성자]에 적용될 수 있으며, [소스|컴파일|실행]시에
          적용되도록하고, 해당 어노테이션 적용시 배열로 설정하도록 지정하고, 해당 어노테이션의 취득은
          실행시 취득 활용되도록 정의 
  		 
       @Target({ ElementType.PARAMETER })
       @Retention(RetentionPolicy.RUNTIME)
       @interface ParamAnnotation(
       		public String value();
       		public int defaultValue();
       }     
       
	    활용1.  class UseSample{
        	    public int plus(@ParamAnnotation(value="param1", defalutValue=0) int x,
        	    			@ParamAnnotation(value="param2", defalutValue=0) int y){
        	    	return x + y;			
        	    }
		            
            
            
            
            
            
            
            
            
            
            
            
            
            