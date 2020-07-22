/**
 * 
 */
// 정규식 패턴 : http://ryanswanson.com/regexp/#start

// if(!/^[a-z][0-9]{3}$/.test('a001')){}
String.prototype.validationID = function(){
   // <input type=text name=mem_id/>
   // "a001".validationID();
   return /^[a-z][0-9]{3}$/.test(this);
};
String.prototype.validationPWD = function(){
   // "asdfasdf".validationPWD()
   return/^[a-z0-9]{4,15}$/.test(this);
};
String.prototype.validationBIR = function(){
   // "1999-01-01".validationBIR()
   // 1999/01/01
   // 19990101
   // 990101
   return /^(19\d{2}|20\d{2})-(0[1-9]|1[0-2])-(0[1-9]|[1-2][1-2]\d|3[0-1])$/.test(this);
};
String.prototype.validationREGNO = function(){
   // "111111-1111111".validationREGNO()
   // 1 1 1 1 1 1  1 1 1 1 1 1       1(매직넘버)
   // * * * * * *  * * * * * * 
   // 2+3+4+5+6+7+ 8+9+2+3+4+5  = 합
   //  (11-(합%11))%10 = 결과값
   //  if(결과값 == 매직넘버){ 유효 } else { 무효 }
   
   var moto = this.replace('-', '');
   var magicNum = this.substr(12, 1);
   var sum = 0;
   var checkSum = '234567892345';
   for(var i=0; i<12; i++){
      sum += moto.charAt(i) * checkSum.charAt(i);      
   }
   
   var finalValue = (11-(sum%11))%10;
   
   if(finalValue == parseInt(magicNum)){
      return true;
   }else{
      return false;
   } 
};

String.prototype.validationHOMETEL = function(){
	// 02-111-1111 or 042-1111-1111
	return /^0\d{1,2}-\d{3,4}-\d{4}$/.test(this);
};

String.prototype.validationCOMTEL = function(){
	// 02-111-1111 or 042-1111-1111
	return /^0\d{1,2}-\d{3,4}-\d{4}$/.test(this);
};

String.prototype.validationMAIL = function(){
	// test@test.com or test@test.co.kr
	return /^[a-z0-9]+@[a-z]+(\.[a-z]+){1,2}$/.test(this);
};

String.prototype.validationHP = function(){
	return /^01(0|1|6|7|9)-\d{3,4}-\d{4}$/.test(this);
};
String.prototype.validationDONG = function(){
	//'대흥동'.validationDONG()
	return /^[가-힣]{2,6}$/.test(this);
};

String.prototype.validationTITLE = function(){
	return /^[가-힣a-zA-Z]+{1,30}$/.text(this);
}
String.prototype.validationNICKNAME = function(){
	return /^[가-힣]{2,5}$/.test(this)
}


