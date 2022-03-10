let app2 = new Vue({
    el: ".app2",
    methods: {
        changeDisplay: () => {
            let start = document.querySelector(".new-card-info");
            let color = document.querySelector(".color-fieldset");
            let formContainer = document.querySelector(".form-container");
            let type = document.querySelector(".type");
            
            console.log("hola")
            if(!start.classList.contains("display-none")){
                start.classList.add("display-none");
                
                console.log(formContainer)
                formContainer.classList.remove("display-none");
                color.classList.remove("display-none");
            }
            formContainer.classList.remove("display-none");
            if(!color.classList.contains("display-none") && type.classList.contains("display-none")){
                color.classList.remove("display-none");
            }
        }
    }
});