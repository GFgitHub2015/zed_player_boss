
/*  
**补充知识：
1，document.querySelectorAll()与document.querySelector是浏览器原声方法，返回的是dom对象或dom对象数组 （ie6,ie7不支持），性能比jq好；
	 
2，Array.prototype.slice.call()能将具有length属性的对象转成数组；
	先这样理解：
	Array.prototype.slice=function(){
	  //代码 this等等
	}
	Array.prototype.slice()调用时，表示是一个方法调用(因为没有new，也不是函数调用)，this指的是Array.prototype对象。
	然后Array.prototype.slice.call(param),改为了间接调用，也改变了this的指向。this指的是param。
	至于Array.prototype.slice里面的具体代码是什么，我们并不知道，但是this已经指向了param，因为里面的代码可以对param进行操作。
	相当于 param=new Object();然后param也可以调用slice方法，所以最后是 param.slice()这样调用的。

3，forEach()是原生方法，用来循环数组；
**
*/

/*写法一：*/
// var elems = Array.prototype.slice.call(document.querySelectorAll('.js-switch'));
// elems.forEach(function(html) {
//     var switchery = new Switchery(html);
// });


/*写法二：*/ 
$('.js-switch').each(function(index, el) {
	$(el).get(0);
	var switchery = new Switchery($(el)[0]);
});

/*写法三：*/
// var elems =$('.js-switch');
// elems.toArray().forEach(function(el) {
//     var switchery = new Switchery(el);
// });

// console.log($.type(elems));   //结果是object 
// console.log($.type(elems.toArray())); //结果是array   这两个是有区别的。

// 共有三种写法


// Colored switches
// var blue = document.querySelector('.js-switch-blue');
var switchery = new Switchery($('.js-switch-blue')[0], { color: '#41b7f1' });

var pink = document.querySelector('.js-switch-pink');
var switchery = new Switchery(pink, { color: '#ff7791' });

var teal = document.querySelector('.js-switch-teal');
var switchery = new Switchery(teal, { color: '#3cc8ad' });

var red = document.querySelector('.js-switch-red');
var switchery = new Switchery(red, { color: '#db5554' });

var yellow = document.querySelector('.js-switch-yellow');
var switchery = new Switchery(yellow, { color: '#fec200' });

