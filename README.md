# :pushpin: GreenGram
>GreenGram은 간단한 SNS 구현 사이트입니다.

</br>

## 1. 제작 기간 & 참여 인원
- 2023년 6월 28일 ~ 7월 10일
- 개인 프로젝트

</br>

## 2. 사용 기술
#### `Back-end`
  - Java 11
  - Spring Boot 4.18
  - Maven
  - jsp
  - Spring Data JPA
  - MariaDB 10.6
#### `Front-end`
  - css
  - JavaScript

</br>

## 3. ERD 설계
![image](https://github.com/HJM95/greengram/assets/122984222/dea0f4e1-577a-4ff5-a955-209ddce83e88)


## 4. 기능
GreenGram의 주요 기능은 이미지와 스토리의 업로드 입니다.
사용자는 단지 이미지를 선택하고, 그에 맞는 스토리를 입력하면 끝입니다.
이 단순한 기능의 흐름을 보면, 서비스가 어떻게 동작하는지 알 수 있습니다.
  

<details>
<summary><b>핵심 기능 설명 펼치기</b></summary>
<div markdown="1">

### 4.1. 전체 흐름

![image](https://github.com/HJM95/greengram/assets/122984222/31efff46-58a1-492a-b9f4-4cc00d0829b1)




### 4.2. Controller
![image](https://github.com/HJM95/greengram/assets/122984222/80b1aabd-1d40-4042-8767-46435a188496)



- **요청 처리** :pushpin: [코드 확인](https://github.com/HJM95/greengram/tree/main/src/main/java/com/hjm/greengram/web)
  - Controller에서는 요청을 화면단에서 넘어온 요청을 받고, Service 계층에 로직 처리를 위임합니다.

- **결과 응답** :pushpin: [코드 확인]()
  - Service 계층에서 넘어온 로직 처리 결과(메세지)를 화면단에 응답해줍니다.

### 4.3. Service

![image](https://github.com/HJM95/greengram/assets/122984222/9d144d27-72f7-4d40-a944-9660a1e1663a)

- **프로세스 처리** :pushpin: [코드 확인](https://github.com/HJM95/greengram/tree/main/src/main/java/com/hjm/greengram/service)
  - Controller부터 호출된 Service는 내부 비즈니스 로직을 처리합니다.

### 4.4. Repository

![image](https://github.com/HJM95/greengram/assets/122984222/76520689-a63a-4fd9-8bad-7c4d3ccb4575)


- **컨텐츠 저장** :pushpin: [코드 확인](https://github.com/HJM95/greengram/tree/main/src/main/java/com/hjm/greengram/domain)
  - Service단에서 정상적으로 수행된 비즈니스 로직을 DB에 저장합니다.

</div>
</details>

</br>

## 5. 트러블 슈팅
### 5.1. 오류 처리 문제에 대한 고민
- Controller단에서 발생한 오류 처리에 관하여 처음에는 @responsebody를 사용해 클라이언트에게 오류내용을 전송하고자 했습니다. 이 방법은 오류시에는 리턴하기 쉬우나 정상값에는 데이터값이 아닌 파일값(String 주소 값)이 리턴되야하는 문제가 있었습니다.

- 해결법: @responsebody가 아닌 일반 Controller 형식으로 구성하되, BindingError를 사용하여 에러 발생시에는 핸들러에서 오류 텍스트값
  을 리턴, 정상 실행시에는 정상적인 파일값(String 주소값)이 리턴되도록 구성하였습니다.

~~~java
/**
 * (예시) 회원가입 Controller
 */
@RequiredArgsConstructor // final 필드를 DI 할때 사용
@Controller // 1. IoC 2. 파일을 리턴하는 컨트롤러
public class AuthController {

	private final AuthService authService;

	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}

	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}

	// 회원가입버튼 -> /auth/signup -> /auth/signin
	// 회원가입버튼 X
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key=value (x-www-form-urlencoded)

		// User < - SignupDto
		User user = signupDto.toEntity();
		authService.회원가입(user);
		// System.out.println(userEntity);

		// 로그를 남기는 후처리!!
		return "auth/signin";

	}
}
~~~

</div>
</details>

</br>


