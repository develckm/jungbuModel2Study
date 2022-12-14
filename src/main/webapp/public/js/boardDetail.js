
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

async function replyUpdateReq(e){
	//this  event가 발생한 node	
	let replyNo=e.target.value;
	let updateUrl="./replyUpdate.do?replyNo="+replyNo;
	try{
		let resp=await fetch(updateUrl,{method:"GET"});
		if(resp.status==200){
			let html=await resp.text();
			const li=document.querySelector("#replyLi"+replyNo)
			li.innerHTML=html;					
		}else if(resp.status==400){ 
			let json=await resp.json();
			if(json.status==-1){
				alert("로그인 하세요");
			}else if(json.status==-2){
				alert("작성한 사람만 수정 가능");
			}
		}else{
			alert("요청한 페이지를 찾을 수 없습니다.");
		}		
	}catch(e){
		alert("서버를 요청할 수 없음");
	}
}
function replyUpdateAct(e){
	const form=e.target.form; //입렵요소에만 있는 선택자 form
	let replyNo=form.replyNo.value;
	const data=new FormData(form); //blob 모든 파라미터를 서블릿에 전달
	let updateUrl="./replyUpdate.do?replyNo="+replyNo;
	//{insert : 0,1 }		
	//asyn 함수로 바꾸고 resp.status가 400일때 로그인과 유저 처리하세요!
	fetch(updateUrl,{method:"POST",body:data})
		.then((resp)=>{
			if(resp.status==200){
				return resp.json();
			}else{
				alert("통신실패! 다시 시도하세요.")
			}
		}).then((json)=>{
			if(json.update==1){
				alert("수정 성공");
				reload()
			}else{
				alert("수정 실패! 새로 고치고 다시 시도하세요.(삭제된 레코드이거나 db 통신 오류)")
			}
		});
}
function replyDeletAct(e){
	const form=e.target.form;
	let replyNo=form.replyNo.value;
	let deleteUrl="./replyUpdate.do?replyNo="+replyNo;
	fetch(deleteUrl,{method:"DELETE"})
		.then((resp)=>{
			if(resp.status==200){
				return resp.json()
			}else{
				alert("통신 실패, 다시 시도하세요.");
			}
		}).then((json)=>{
			let msg="";
			switch(json.delete){
				case 1: msg="삭제 성공"; reload(); break;
				case 0: msg="이미 삭제된 레코드 입니다."; break;
				case -1: msg="로그인을 하세요."; break;
				case -2: msg="작성자만 삭제 가능합니다."; break;
				case -3: msg="통신 장애, 다시 시도하세요."; break;
			}
			alert(msg);
		})
}


replyInsert.onsubmit=function (e){
	e.preventDefault();//이벤트 중지! 
	let insertUrl="./replyInsert.do";
	const data=new FormData(this);
	//FormData : blob data 전송시 사용가능
	fetch(insertUrl,{method:"POST",body:data})
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
async function boardPreferModify(e,prefer){//prefer 1: 좋아요,0:싫어요
	//promise 객체를 await로 호출할 수 있는 함수 : await를 호출하면 .then((param)=>{}) param이 반환된다.
	let boardNo=e.target.value;
	let url="./boardPreferModify.do?boardNo="+boardNo+"&prefer="+prefer;
	//fetch(url).then((resp)=>resp.json()).then((json)=>{})
	let msg=(prefer==1)?"좋아요":"싫어요";
	let resp=await fetch(url);
	if(resp.status==200){
		let json=await resp.json();
		console.log(json);
		switch(json.modify){
			case -1 :msg="로그인을 하세요!"; break;
			case 0 :msg+=" 수정 실패!"; break;
			case 1 :msg+=" 등록 성공"; break;
			case 2 :msg+=" 수정 성공"; break;
			case 3 :msg+=" 삭제 성공"; break;
		}
		if(json.modify>0){ 
			let resp=await fetch("./boardPreferDetail.do?boardNo="+boardNo);
			if(resp.status==200){
				let text=await resp.text();//html
				preferContainer.innerHTML=text;
			}else{
				msg+=" 했지만 페이지를 불러오지 못함(새로고침 하세요!)"
			}
		}
	}else{
		msg="통신 실패!";
	}
	alert(msg);
}







