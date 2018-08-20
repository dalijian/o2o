/**
 * 
 */
function changeVerifyCode(img){
    //math.floor 产生随机数
    img.src = "../Kaptcha?" + Math.floor(Math.random()*100);
}