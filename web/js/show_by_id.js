/**
 * @param {String} elem_id name of elem that should to be toggled(display none/block)
 * @param {String} display_on display style when object is on
 * @param {String} display_off display style when object is off
 * @param {String} hide_class all elem.display with this class will firstly seted to display_off
 */
function ShowById(elem_id, display_on = "block", display_off = "none", hide_class = "") {
    let elem = document.getElementById(elem_id);
    if(!elem)return;
    let elem_display = elem.style.display;
    hide_by_class_name(hide_class, display_off);
    elem.style.display = (elem_display === display_off) ? display_on : display_off;
}

function ShById(elem_id, hide_cl = "") {ShowById(elem_id, "block", "none", hide_cl);}

/**
 * @param {String} hide_class all elem.display with this class will seted to display_off
 * @param {String} display_off display style when object is off
 */
function hide_by_class_name(hide_class, display_off = "none"){
    if(!hide_class || hide_class.length === 0)return;
    let elems = document.getElementsByClassName(hide_class);
    if(!elems)return;
    for(let i = 0; i < elems.length; i++)elems[i].style.display = display_off;
}