/**
 * @param {String} elem_id name of elem that should to be toggled(display none/block)
 * @param {String} display_on display style when object is on
 * @param {String} display_off display style when object is off
 */
function ShowById(elem_id, display_on = "block", display_off = "none") {
    let elem = document.getElementById(elem_id);
    elem.style.display = (elem.style.display === display_off) ? display_on : display_off;
}
