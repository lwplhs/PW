if(document.querySelector(".float-menu")){
    var plus = document.querySelector(".plus");
    var floatMenu = document.querySelector(".float-menu");
    floatMenu.addEventListener("mouseover",function(e){
        floatMenu.className = "float-menu open";
        //floatMenu.className.indexOf("open") > -1?floatMenu.className="float-menu" :
    },false);
    floatMenu.addEventListener("mouseout",function(){
        //floatMenu.className.indexOf("open") > -1? :floatMenu.className = "float-menu open"
        floatMenu.className="float-menu";
    },false);

    var clickstatus = false;
    var lastX = 0;
    var lastY = 0;
    var lastcX = 0;
    var lastcY = 0;
    floatMenu.addEventListener("mousedown",function (e) {
        if(e.target.className.indexOf('cross')!=-1){
            clickstatus = true;
            lastX = floatMenu.offsetLeft;
            lastY = floatMenu.offsetTop;
            lastcX = e.clientX;
            lastcY = e.clientY;
        }
    },false);
    floatMenu.addEventListener("mousemove",function (e) {
        if(clickstatus){
            floatMenu.style.left = lastX + (e.clientX - lastcX) + 'px' ;
            floatMenu.style.top = lastY + (e.clientY-lastcY) + 'px';
        }
    });
    floatMenu.addEventListener("mouseup",function (e) {
        clickstatus = false;
        lastX=0;
        lastY=0;
        lastcX=0;
        lastcY=0;
    });

}