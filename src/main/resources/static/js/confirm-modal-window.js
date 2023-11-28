export function openConfirmModalWindow(onConfirmAction, param){
    const modal = document.getElementById("myModal");
    const confirmYes = document.getElementById("confirmYes");
    const confirmNo = document.getElementById("confirmNo");

    modal.style.display = "block";

    confirmYes.addEventListener("click",  () => {
        onConfirmAction(param);
        modal.style.display = "none";
    }, {once : true});

    confirmNo.addEventListener("click", () => {
        modal.style.display = "none";
    }, {once : true});

    window.addEventListener("click",  (event) => {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    }, {once : true});
}