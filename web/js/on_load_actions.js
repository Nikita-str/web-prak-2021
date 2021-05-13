
function add_await_to_href(){
    let lblock = document.getElementsByClassName("left-block");
    if(!lblock)return;
    let elem = lblock.item(0);
    if(!elem)return;
    let a = elem.getElementsByTagName("a");
    for(let i = 0; i < a.length; i++){
        a[i].onclick = function (){ShowById('await');}
    }
}

window.onload = function() {
    add_await_to_href();
}