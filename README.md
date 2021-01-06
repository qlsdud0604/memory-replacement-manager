# 메모리 교체 매니저
## 1. 프로젝트의 목적
* 다양한 페이지 교체 알고리즘을 직접 구현해 봄으로써 동작과정을 이해한다.
* 각 알고리즘의 페이지 교체 과정을 분석하여 특성을 파악한다.
* 구현한 프로그램을 통해 모의실험을 함으로써 각 알고리즘을 평가해 본다.

-----
## 2. 프로젝트의 내용
* 여러 가지 페이지 교체 알고리즘에 대응하는 벤치마킹 프로그램을 구현한다.
* 구현한 벤치마킹 프로그램을 이용해 모의실험을 진행한다.
* 모의실험을 통해 각각의 알고리즘을 평가한다.

-----
## 3. FIFO 알고리즘
**1) 알고리즘의 구현**
```
public void showInformation() {
	for (int i = 0; i < numberOfFrame; i++)
		frame[i] = -1;

	int hit = 0;
	int fault = 0;

	int indexOfFrame = 0;
	boolean check;

	for (int i = 0; i < numberOfInput; i++) {
		check = false;

		for (int j = 0; j < numberOfFrame; j++) {
			if (frame[j] == referenceString[i]) {
				check = true;

				hit += 1;
			}
		}

		if (check == false) {
			frame[indexOfFrame] = referenceString[i];
			indexOfFrame++;

			if (indexOfFrame >= numberOfFrame)
				indexOfFrame = 0;

			fault += 1;
		}
		for (int j = 0; j < numberOfFrame; j++) {
			this.memory_layout[j][i] = frame[j];
		}
	}
	float ratio = (float) hit / (float) numberOfInput;
	System.out.println("--------------------------------------------------------");
	System.out.println("FIFO ALGORITHM");
	System.out.println("--------------------------------------------------------");
	System.out.println("HIT     FAULT     HIT RATIO     FAULT RATIO     EAT");
	System.out.print(" " + hit);
	System.out.printf("%9d", fault);
	System.out.printf("%13.2f", ratio);
	System.out.printf("%15.2f", 1 - ratio);
	System.out.printf("%13.2f", (200 + (1 - ratio) * 7999800) / 1000);
	System.out.println("(us)");
	System.out.println("--------------------------------------------------------");
}
```
ㆍ FIFO 페이지 교체 알고리즘의 hit, fault를 구하는 알고리즘이다.   
ㆍ 사용자로부터 입력받은 총 페이지 수만큼 반복을 하며, 페이지 하나를 참조할 경우에 대한 프레임내의 페이지 할당과정을 알고리즘으로 구현한 것이다.   
ㆍ 현재 프레임 내 같은 페이지가 있을 경우 hit의 값을 증가 시킨다.   
ㆍ 참조할 페이지가 현재 프레임에 없을 경우 페이지의 하위 주소부터 시작하여 주소를 1씩 증가시키며 페이지를 교체하는 방식의 알고리즘이다.   

**2) 실행 결과**

<img src="https://user-images.githubusercontent.com/61148914/89907186-4c4a6280-dc27-11ea-88ae-761c667235a7.JPG" width="45%">

ㆍ FIFO 페이지 교체 알고리즘의 실제 동작 결과를 나타낸 그림이다.   
ㆍ 프레임의 수를 3, 할당할 페이지의 수를 20을 주었다.   
ㆍ 참조 문자열로 7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1의 값을 주었다.   
ㆍ 입력한 값에 대한 알고리즘의 결과가 정확하게 출력하고 있음을 확인하였다.   

<img src="https://user-images.githubusercontent.com/61148914/89907194-4e142600-dc27-11ea-816f-17fe2665ef4e.JPG" width="70%">

ㆍ 위에서 언급한 참조 문자열에 대한 실제 프레임의 교체 과정을 보여주고 있는 그림이다.   
ㆍ 프레임의 교체 과정 또한 정확하다는 것을 확인하였다.   

-----
## 4. LFU 알고리즘
**1) 알고리즘의 구현**
```
public void showInformation() {
	for (int i = 0; i < numberOfFrame; i++) {
		frame[i] = -1;
		frameFrequency[i] = -1;
	}

	int hit = 0;
	int min = 0;
	int frameNum = 0;

	boolean check = true;

	for (int i = 0; i < numberOfInput; i++) {
		check = true;

		int currentPage = referenceString[i];

		for (int j = 0; j < numberOfFrame; j++) {
			if (frame[j] == currentPage) {
				check = false;
				hit++;
				pageFrequency[currentPage]++;
				break;
			}
		}
		if (check) {
			if (i >= numberOfFrame) {
				for (int j = 0; j < numberOfFrame; j++) {
					frameNum = frame[j];
					frameFrequency[j] = pageFrequency[frameNum];
				}
				min = frameFrequency[0];
				for (int j = 0; j < numberOfFrame; j++) {
					if (frameFrequency[j] < min) {
						min = frameFrequency[j];
					}
				}
				for (int j = 0; j < numberOfFrame; j++) {
					if (frameFrequency[j] == min) {
						pageFrequency[currentPage]++;
						frame[j] = currentPage;
						break;
					}
				}
			} else {
				frame[i] = currentPage;
				pageFrequency[currentPage]++;
			}
		}
		for (int j = 0; j < numberOfFrame; j++) {
			this.memory_layout[j][i] = frame[j];
		}
	}
	float ratio = (float) hit / (float) numberOfInput;
	System.out.println("--------------------------------------------------------");
	System.out.println("LFU ALGORITHM");
	System.out.println("--------------------------------------------------------");
	System.out.println("HIT     FAULT     HIT RATIO     FAULT RATIO     EAT");
	System.out.print(" " + hit);
	System.out.printf("%9d", numberOfInput - hit);
	System.out.printf("%13.2f", ratio);
	System.out.printf("%15.2f", 1 - ratio);
	System.out.printf("%13.2f", (200 + (1 - ratio) * 7999800) / 1000);
	System.out.println("(us)");
	System.out.println("--------------------------------------------------------");
}
```
ㆍ LFU 교체 알고리즘을 구현한 코드이다.   
ㆍ LFU 교체 알고리즘 또한 총 페이지 수만큼 반복을 하며, 페이지 하나를 참조할 경우를 고려하여 구현 하였다.   
ㆍ 페이지에 대한 참조 횟수를 저장하기 위해서 페이지의 수와 같은 크기로 pageFrequency[] 배열을 선언하였다.   
ㆍ  현재 참조할 페이지의 인덱스에 해당하는 pageFrequency[] 원소의 값을 1씩 증가 시키며, 새로운 페이지가 할당될 때 현재 프레임의 있는 페이지의 참조 횟수를 서로 비교하며, 가장 작은 값이 바뀌는 방식으로 알고리즘을 구현하였다.   

**2) 실행 결과**

<img src="https://user-images.githubusercontent.com/61148914/89993677-0ee6e300-dcc2-11ea-8c92-3acb5db8281b.JPG" width="45%">

ㆍ LFU 페이지 교체 알고리즘의 실제 동작 결과를 나타낸 그림이다.   
ㆍ 프레임의 수를 3, 할당할 페이지의 수를 20을 주었다.   
ㆍ 참조 문자열로 7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1의 값을 주었다.   
ㆍ 입력한 값에 대한 알고리즘의 결과가 정확하게 출력하고 있음을 확인하였다.   

<img src="https://user-images.githubusercontent.com/61148914/89993682-10b0a680-dcc2-11ea-9d9d-736d5f3f8dba.JPG" width="70%">

ㆍ 참조 문자열에 대한 실제 프레임의 교체 과정을 보여주고 있는 그림이다.   
ㆍ 프레임의 교체 과정 또한 정확하다는 것을 확인하였다.   

-----
## 5. MFU 알고리즘
**1) 알고리즘의 구현**
```
public void showInformation() {
	for (int i = 0; i < numberOfFrame; i++) {
		frame[i] = -1;
		frameFrequency[i] = -1;
	}

	int hit = 0;
	int max = 0;
	int frameNum = 0;

	boolean check = true;

	for (int i = 0; i < numberOfInput; i++) {
		check = true;

		int currentPage = referenceString[i];

		for (int j = 0; j < numberOfFrame; j++) {
			if (frame[j] == currentPage) {
				check = false;
				hit++;
				pageFrequency[currentPage]++;
				break;
			}
		}
		if (check) {
			if (i >= numberOfFrame) {
				for (int j = 0; j < numberOfFrame; j++) {
					frameNum = frame[j];
					frameFrequency[j] = pageFrequency[frameNum];
				}
				max = frameFrequency[0];
				for (int j = 0; j < numberOfFrame; j++) {
					if (frameFrequency[j] > max) {
						max = frameFrequency[j];
					}
				}
				for (int j = 0; j < numberOfFrame; j++) {
					if (frameFrequency[j] == max) {
						pageFrequency[currentPage]++;
						frame[j] = currentPage;
						break;
					}
				}
			} else {
				frame[i] = currentPage;
				pageFrequency[currentPage]++;
			}
		}
		for (int j = 0; j < numberOfFrame; j++) {
			this.memory_layout[j][i] = frame[j];
		}
	}
	float ratio = (float) hit / (float) numberOfInput;
	System.out.println("--------------------------------------------------------");
	System.out.println("MFU ALGORITHM");
	System.out.println("--------------------------------------------------------");
	System.out.println("HIT     FAULT     HIT RATIO     FAULT RATIO     EAT");
	System.out.print(" " + hit);
	System.out.printf("%9d", numberOfInput - hit);
	System.out.printf("%13.2f", ratio);
	System.out.printf("%15.2f", 1 - ratio);
	System.out.printf("%13.2f", (200 + (1 - ratio) * 7999800) / 1000);
	System.out.println("(us)");
	System.out.println("--------------------------------------------------------");
}
```
ㆍ MFU 교체 알고리즘을 구현한 코드이다.   
ㆍ MFU 교체 알고리즘은 LFU 교체 알고리즘과 매우 유사한 방식으로 구현을 하였다.    
ㆍ 페이지에 대한 참조 횟수를 저장하기 위해서 페이지의 수와 같은 크기로 pageFrequency[] 배열을 선언하였다.   
ㆍ  현재 참조할 페이지의 인덱스에 해당하는 pageFrequency[] 원소의 값을 1씩 증가 시키며, 새로운 페이지가 할당될 때 현재 프레임의 있는 페이지의 참조 횟수를 서로 비교하며, 가장 큰 값이 바뀌는 방식으로 알고리즘을 구현하였다.   

**2) 실행 결과**

<img src="https://user-images.githubusercontent.com/61148914/90123382-b256f700-dd99-11ea-88f5-18a90a6e6124.JPG" width="45%">

ㆍ MFU 페이지 교체 알고리즘의 실제 동작 결과를 나타낸 그림이다.   
ㆍ 프레임의 수를 3, 할당할 페이지의 수를 20을 주었다.   
ㆍ 참조 문자열로 7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1의 값을 주었다.   
ㆍ 입력한 값에 대한 알고리즘의 결과가 정확하게 출력하고 있음을 확인하였다.   

<img src="https://user-images.githubusercontent.com/61148914/90123385-b3882400-dd99-11ea-8574-f752c3ef5083.JPG" width="70%">

ㆍ 참조 문자열에 대한 실제 프레임의 교체 과정을 보여주고 있는 그림이다.   
ㆍ 프레임의 교체 과정 또한 정확하다는 것을 확인하였다.   

-----
## 6. LRU 알고리즘
**1) 알고리즘의 구현**
```
public void showInformation() {
	for (int i = 0; i < numberOfFrame; i++)
		frame[i] = -1;

	int hit = 0;
	int fault = 0;
	boolean frameIsFull = false;
	int indexOfFrame = 0;

	for (int i = 0; i < numberOfInput; i++) {
		int searchPage = -1;

		for (int j = 0; j < numberOfFrame; j++) {
			if (frame[j] == referenceString[i]) {
				searchPage = j;
				hit++;

				break;
			}
		}

		if (searchPage == -1) {

			if (frameIsFull) {
				int longestPage[] = new int[numberOfFrame];
				boolean longestPage_check[] = new boolean[numberOfFrame];

				for (int j = i - 1; j >= 0; j--) {
					for (int k = 0; k < numberOfFrame; k++) {
						if ((frame[k] == referenceString[j]) && (longestPage_check[k] == false)) {
							longestPage[k] = j;
							longestPage_check[k] = true;
							break;
						}
					}
				}

				int min = longestPage[0];
				indexOfFrame = 0;

				for (int j = 0; j < numberOfFrame; j++) {
					if (longestPage[j] == 0)
						longestPage[j] = Integer.MIN_VALUE;
					if (longestPage[j] < min) {
						min = longestPage[j];
						indexOfFrame = j;
					}
				}
			}
			frame[indexOfFrame] = referenceString[i];
			fault++;

			if (!frameIsFull) {
				indexOfFrame++;
				if (indexOfFrame == numberOfFrame) {
					frameIsFull = true;
					indexOfFrame = 0;
				}
			}
		}
		for (int j = 0; j < numberOfFrame; j++) {
			this.memory_layout[j][i] = frame[j];
		}
	}
	float ratio = (float) hit / (float) numberOfInput;
	System.out.println("--------------------------------------------------------");
	System.out.println("LRU ALGORITHM");
	System.out.println("--------------------------------------------------------");
	System.out.println("HIT     FAULT     HIT RATIO     FAULT RATIO     EAT");
	System.out.print(" " + hit);
	System.out.printf("%9d", fault);
	System.out.printf("%13.2f", ratio);
	System.out.printf("%15.2f", 1 - ratio);
	System.out.printf("%13.2f", (200 + (1 - ratio) * 7999800) / 1000);
	System.out.println("(us)");
	System.out.println("--------------------------------------------------------");
}
```
ㆍ LRU 페이지 교체 알고리즘의 코드이다.   
ㆍ 역시 다른 페이지 교체 알고리즘과 마찬가지로 프레임 내에 현재 할당하고자 하는 페이지가 있을 경우 hit을 1씩 증가시키며, hit을 계산한다.   
ㆍ 프레임 내에 현재 할당하려는 페이지가 없을 때는 빈 프레임이 있을 경우는 그대로 페이지를 할당하고, 빈 프레임이 없을 경우에는 longestPage[] 정수형 배열에 앞서 참조된 페이지의 인덱스를 부여하여, 가장 멀리 떨어진 페이지를 인덱스로 비교를 한다.   
ㆍ 인덱스를 부여받은 longestPage[] 배열 내에 가장 멀리 떨어진 페이지를 선택한 후 그 페이지를 현재 할당하고자 하는 페이지와 교체하는 방식이다.   

**2) 실행 결과**

<img src="https://user-images.githubusercontent.com/61148914/90216884-b6872100-de3a-11ea-8390-693965ec8b40.JPG" width="45%">

ㆍ LRU 페이지 교체 알고리즘의 실제 동작 결과를 나타낸 그림이다.   
ㆍ 프레임의 수를 3, 할당할 페이지의 수를 20을 주었다.   
ㆍ 참조 문자열로 7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1의 값을 주었다.   
ㆍ 입력한 값에 대한 알고리즘의 결과가 정확하게 출력하고 있음을 확인하였다.    

<img src="https://user-images.githubusercontent.com/61148914/90216881-b555f400-de3a-11ea-8333-9a06f85252bd.JPG" width="70%">

ㆍ 참조 문자열에 대한 실제 프레임의 교체 과정을 보여주고 있는 그림이다.      
ㆍ 프레임의 교체 과정 또한 정확하다는 것을 확인하였다.    

-----
## 7. Optimal 알고리즘
**1) 알고리즘의 구현**
```
public void showInformation() {
	for (int i = 0; i < numberOfFrame; i++)
		frame[i] = -1;

	int hit = 0;
	int fault = 0;
	boolean frameIsFull = false;
	int indexOfFrame = 0;

	for (int i = 0; i < numberOfInput; i++) {
		int searchPage = -1;

		for (int j = 0; j < numberOfFrame; j++) {
			if (frame[j] == referenceString[i]) {
				searchPage = j;
				hit++;

				break;
			}
		}

		if (searchPage == -1) {

			if (frameIsFull) {
				int longestPage[] = new int[numberOfFrame];
				boolean longestPage_check[] = new boolean[numberOfFrame];

				for (int j = i + 1; j < numberOfInput; j++) {
					for (int k = 0; k < numberOfFrame; k++) {
						if ((frame[k] == referenceString[j]) && (longestPage_check[k] == false)) {
							longestPage[k] = j;
							longestPage_check[k] = true;
							break;
						}
					}
				}

				int max = longestPage[0];
				indexOfFrame = 0;

				for (int j = 0; j < numberOfFrame; j++) {
					if (longestPage[j] == 0)
						longestPage[j] = Integer.MAX_VALUE;
					if (longestPage[j] > max) {
						max = longestPage[j];
						indexOfFrame = j;
					}
				}
			}
			frame[indexOfFrame] = referenceString[i];
			fault++;

			if (!frameIsFull) {
				indexOfFrame++;
				if (indexOfFrame == numberOfFrame) {
					frameIsFull = true;
					indexOfFrame = 0;
				}
			}
		}
		for (int j = 0; j < numberOfFrame; j++) {
			this.memory_layout[j][i] = frame[j];
		}
	}
	float ratio = (float) hit / (float) numberOfInput;
	System.out.println("--------------------------------------------------------");
	System.out.println("OPTIMAL ALGORITHM");
	System.out.println("--------------------------------------------------------");
	System.out.println("HIT     FAULT     HIT RATIO     FAULT RATIO     EAT");
	System.out.print(" " + hit);
	System.out.printf("%9d", fault);
	System.out.printf("%13.2f", ratio);
	System.out.printf("%15.2f", 1 - ratio);
	System.out.printf("%13.2f", (200 + (1 - ratio) * 7999800) / 1000);
	System.out.println("(us)");
	System.out.println("--------------------------------------------------------");
}
```
ㆍ Optimal 페이지 교체 알고리즘의 코드이다.   
ㆍ LRU 페이지 교체 알고리즘과 매우 유사한 방식으로 구현을 하였다.   
ㆍ 프레임 내에 현재 할당하려는 페이지가 없을 때는 빈 프레임이 있을 경우는 그대로 페이지를 할당하고, 빈 프레임이 없을 경우에는 longestPage[] 정수형 배열에 앞서 참조가 될 페이지의 인덱스를 부여하여, 가장 멀리 떨어진 페이지를 인덱스로 비교를 한다.   
ㆍ 인덱스를 부여받은 longestPage[] 배열 내에 가장 멀리 떨어진 페이지를 선택한 후 그 페이지를 현재 할당하고자 하는 페이지와 교체하는 방식이다.   

**2) 실행 결과**

<img src="https://user-images.githubusercontent.com/61148914/90330032-b75bb680-dfe4-11ea-8c5e-c34b0cdd3d7b.JPG" width="45%">

ㆍ LRU 페이지 교체 알고리즘의 실제 동작 결과를 나타낸 그림이다.   
ㆍ 프레임의 수를 3, 할당할 페이지의 수를 20을 주었다.   
ㆍ 참조 문자열로 7 0 1 2 0 3 0 4 2 3 0 3 2 1 2 0 1 7 0 1의 값을 주었다.   
ㆍ 입력한 값에 대한 알고리즘의 결과가 정확하게 출력하고 있음을 확인하였다.   

<img src="https://user-images.githubusercontent.com/61148914/90330030-b591f300-dfe4-11ea-915b-09d7e8aabfc8.JPG" width="70%">

ㆍ 참조 문자열에 대한 실제 프레임의 교체 과정을 보여주고 있는 그림이다.       
ㆍ 프레임의 교체 과정 또한 정확하다는 것을 확인하였다.   

-----
## 8. 페이지 교체 알고리즘의 테스트 및 결과 분석 
**1) 페이지 교체 알고리즘의 테스트**

<img src="https://user-images.githubusercontent.com/61148914/90492010-a5604c00-e17b-11ea-89f0-f35d7760c638.JPG" width="55%">

ㆍ 각 페이지 교체 알고리즘을 공평하게 평가하기 위하여 위 표와 같은 입력 예제를 각 알고리즘에게 동일하게 주어 테스트를 진행하였다.   
ㆍ 각 페이지 알고리즘은 Effective Access Time (EAT)으로 평가를 하였다.   

<img src="https://user-images.githubusercontent.com/61148914/90492004-a2fdf200-e17b-11ea-945c-d3ecf9cb3e39.JPG" width="55%">

ㆍ 4가지 입력 예제에 대해 각 알고리즘의 hit, fault, EAT지수를 표로 나타낸 것이다.   
ㆍ EAT지수는 메모리 접근 시간을 200 nanosecond로 fault의 평균 시간을 8 millisecond의 시간으로 가정을 하고 계산한 수치이다.   
ㆍ 각 알고리즘의 성능평가는 EAT지수에 의해서 평가한다.   

**2) 테스트의 결과 분석**

<img src="https://user-images.githubusercontent.com/61148914/90492637-5ebf2180-e17c-11ea-90f4-52e99ea9bebe.JPG" width="40%">

ㆍ 예제1에 대한 각 알고리즘의 EAT지수를 막대그래프로 나타내 보았다.   
ㆍ 그래프를 보면 알 수 있듯이 Optimal의 성능이 가장 좋고 LRU의 성능이 가장 않 좋다는 것을 볼 수 있다.   
ㆍ FIFO, LFU, MFU 알고리즘은 EAT지수가 똑같다는 것을 알 수 있다.   

<img src="https://user-images.githubusercontent.com/61148914/90492639-5f57b800-e17c-11ea-90d7-710bfbc5c79f.JPG" width="40%">

ㆍ 예제2에 대한 각 알고리즘의 EAT지수를 막대그래프로 나타내 보았다.   
ㆍ 예제1번과 마찬가지로 Optimal의 성능이 가장 좋고 LRU의 성능이 가장 좋지 않다는 것을 볼 수 있다.  

<img src="https://user-images.githubusercontent.com/61148914/90492642-5ff04e80-e17c-11ea-89c5-ad6a01d69f6a.JPG" width="40%">

ㆍ 예제3에 대한 각 알고리즘의 EAT지수를 막대그래프로 나타내 보았다.   
ㆍ 그래프를 보면 Optimal의 성능이 가장 좋고 차례대로 FIFO의 성능이 좋지 않다는 것을 알 수 있다.   

<img src="https://user-images.githubusercontent.com/61148914/90492632-5d8df480-e17c-11ea-857d-b18911e72b68.JPG" width="40%">

ㆍ 예제4에 대한 각 알고리즘의 EAT지수를 막대그래프로 나타내 보았다.   
ㆍ 역시나 Optimal의 성능이 가장 좋다는 것을 알 수 있다.   

-----
## 8. 페이지 교체 알고리즘의 분석과 평가
**1) FIFO 알고리즘**   
ㆍ 메모리에 제일 먼저 들어오는 페이지를 제일먼저 교체해주는 방법이다.   
ㆍ 구현하기가 제일 간단한 알고리즘이었다.   
ㆍ 테스트를 진행할 때 4가지 예제에 대해서 가장 성능이 좋지 못했던 알고리즘이기도 하다.   

**2) LFU 알고리즘**   
ㆍ 참조횟수가 가장 적은 페이지를 교체해주는 방법이다.   
ㆍ 테스트 결과 대체로 FIFO 알고리즘보다 괜찮은 성능을 보였다.   
ㆍ 벤치마킹 프로그램을 구현할 때 참조횟수를 저장하는 방법으로 할당될 페이지의 수만큼 배열을 선언했다. 실제 시스템 상에서 구현을 하면 공간 복잡도가 높아 질 우려가 있다고 생각한다.   

**3) MFU 알고리즘**   
ㆍ LFU 알고리즘과는 반대로 참조횟수가 가장 많은 페이지를 교체해주는 방식이다.   
ㆍ 테스트 결과 대체로 FIFO 알고리즘보다는 괜찮을 성능을 보였으며, LFU 알고리즘과 마찬가지로 중간 이상의 성능을 보였다.   
ㆍ MFU 알고리즘 또한 참조횟수를 저장하는 방법으로 할당될 페이지의 수만큼 배열을 선언했다. 실제 시스템 상에서 구현을 하면 공간 복잡도가 높아 질 우려가 있으니 주의해야 할 필요가 있다.   

**4) LRU 알고리즘**   
ㆍ 메모리에서 가장 오랜 시간을 보낸 페이지를 교체하는 방식의 알고리즘이다.   
ㆍ 예제1, 예제2 에서는 가장 성능이 안 좋았지만, 예제3, 예제4의 경우 우수한 성을을 보였다.   
ㆍ 대체적으로 좋은 성능을 보인다고 알려진 알고리즘이다. 좀 더 많은 예제에 대해 테스트를 진행해 보아야 성능을 정확히 파악할 수 있다고 생각한다.   
  
**5) Optimal 알고리즘**   
ㆍ 앞으로 참조될 확률이 제일 적은 페이지를 교체하는 방식의 알고리즘이다.   
ㆍ 모든 예제에 대해서 가장 우수한 성능을 보인 알고리즘이다.   
ㆍ 특정 페이지가 미래에 참조될 확률을 구한다는 것 자체가 불가능하므로, 현실적으로 시스템에 구현하기란 어려울 것이라고 생각한다.   

-----
## 9. 프로젝트 수행 중 어려웠던 점
 * 각 알고리즘에 대한 학습이 부족한 시기에 시작한 프로젝트라서 초반에 알고리즘을 구현하는데 어려움이 있었다.
 * 프레임의 교체과정을 표현하는데 어떤식으로 표현을 할까 어려움이 있었다.
 
-----
## 10. 프로젝트를 마치고 느낀 점
 * 여러 가지 페이지 교체 알고리즘에 대한 동작과정을 정확히 파악할 수 있었다.
 * 각 페이지 교체 알고리즘의 장단점 및 특성을 이해할 수 있었다.
