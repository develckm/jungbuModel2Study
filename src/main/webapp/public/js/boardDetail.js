
var a=10; //변수 전역에 선언되는 변수(지역에서 선언이 안됨) window.a
{
	var a="20"; //지역변수로 선언되지 않는다.
}
console.log(a); //"20" : 호이팅 
let b=10; //전역과 지역에 구분되어 선언되는 변수
b="20";
b=true;
const C="상수";
//C=3; //상수는 바꿀수 없는 값(오류)

//자바스크립트의 함수는 함수도 될 수 있고 타입도 될 수 있다.(프로토 타입)
//함수를 생성시 프로토 타입 객체를 만들고 프로토 타입으로 객체를 생성할 수 있다.

let i =sum(10,20); //자바스크립트는 순차적 언어라 먼저 선언되지 않으면 실행할 수 없다.하지만 실행이된다.: 호이스팅
//함수는 자바스크립트에서 타입이 되기 때문에 함수만 먼저 프로토타입으로 생성하고 순차적으로 실행하기 때문에 호이스팅이 발생 
function sum(a,b){return a+b;}
new sum();
function User(a,b){this.a=a;this.b=b;} //타입으로 선언되는 함수는 리턴을 작성해도 실행되지 않는다.
new User(10,20);

let sum2=()=>{}; //객체가 될 수 없는 일반 함수로 호이스팅 되지 않는다.


document.getElementById("replysReload") //노드 1개를 찾는다.
document.getElementsByClassName("className") //배열을 반환
document.getElementsByTagName("div")//배열을 반환 
document.querySelectorAll("#replysReload div.class")//배열반환
document.querySelector("#replysReload div.class")//노드 1개를 찾는다.(복수중에 0번째)

//id와 form의 name을 지정한 node를 변수로 사용할 수 있도록 등록됨 
replysReload.onclick=function(){ console.log("안녕!");} 
//콜백함수 : 바로 실행되지 않고 이벤트가 발생했을때 실행되도록 정의하는 함수

replysReload.addEventListener('click',reload); //이벤트 발생시 여러함수를 동작하게 지정가능

//브라우저가 자바스크립트에서 http 통신을 할 수 있도록 객체를 제공
function reload(){
	let boardNo_str=replysReload.value;
	let request=new XMLHttpRequest();
	request.open("GET","./replyList.do?boardNo="+boardNo_str,true);
	request.onreadystatechange=function(){
		if(request.readyState==4 && request.status==200 ){ //응답이 완료된 상태
			replysContainer.innerHTML=request.responseText;
		}
	}//통신 이벤트(send)가 발생하면 실행되는 함수 
	request.send();	//통신 이벤트
	let html=request.responseText;
	replysContainer.innerHTML=html; 
	//js XMLHttpRequest는 통신 시 스레드를 새로 생성하기 때문에 
	//onreadystatechange 정의하거나 Promise로 스레드를 동기화 해야한다. 
}

replyInsert.onsubmit=function (e){
	e.preventDefault();//이벤트 중지! 
	let insertUrl="./replyInsert.do";
	let param="?boardNo="+replyInsert.boardNo.value
			+"&title="+replyInsert.title.value
			+"&contents="+replyInsert.contents.value
			+"&userId="+replyInsert.userId.value

	fetch(insertUrl+param,{method:"POST"})
		.then((resp)=>resp.json())
		.then((insertJosn)=>{
			if(insertJosn.insert>0){
				alert("등록 성공!")
				reload();
			}else{
				alert("등록 실패!")
			}
		});
}


