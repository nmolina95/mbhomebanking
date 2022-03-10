let app = new Vue({
    el: "#app",
    data: {
        client: {},
        form: {
            fname: "",
            lname: "",
            email: "",
            password: ""
        }
    },
    methods: {
        addClient(){
            if(this.form.fname && this.form.lname && this.form.email.includes("@") && this.form.password){
                //let client = "firstName="+app.form.fnameR+"&lastName="+app.form.lnameR+"&email="+app.form.emailR+"&password="+app.form.passwordR;
                app.register();
            }
        },
        login: () => {
            let user = "email="+app.form.email+"&password="+app.form.password;

            axios.post("/api/login", user, {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(res => {
                    console.log(res);
                    window.location.href = "/web/accounts2.html";
                })
                .catch(err => {
                    console.error(err);
                })
        },
        recoverPassword: () => {
            let recoverPassword = document.querySelector(".recover-password");
            let login = document.querySelector(".login");
            let register = document.querySelector(".register");
            let warning = document.querySelector(".warning");

            app.switchDisplay(login);
            app.switchDisplay(register);
            app.switchDisplay(warning);
            
            recoverPassword.classList.remove("display-none");
        },
        register: () => {
            axios.post('/api/clients',"firstName=" + app.form.fname + "&lastName=" + app.form.lname + "&email=" + app.form.email + "&password=" + app.form.password, {headers:{'content-type':'application/x-www-form-urlencoded'}})
                .then(res => {
                    app.login();
            })
        },
        switchDisplay: (param) => {
            if(!param.classList.contains("display-none")){
                param.classList.add("display-none");
            }
        },
        // Switch between login, register and recover password
        switchForm: () => {
            let login = document.querySelector(".login");
            let register = document.querySelector(".register");
            let recoverPassword = document.querySelector(".recover-password");
            let warning = document.querySelector(".warning");

            if(!login.classList.contains("display-none")){
                login.classList.add("display-none");
                register.classList.remove("display-none");
            }
            else{
                login.classList.remove("display-none");
                register.classList.add("display-none");
            }
            app.switchDisplay(recoverPassword);
            app.switchDisplay(warning);
        },
        switchToLogin: () => {
            let login = document.querySelector(".login");

            if(login.classList.contains("display-none")){
                app.switchForm();
            }
        },
        switchToRegister: () => {
            let login = document.querySelector(".login");

            if(!login.classList.contains("display-none")){
                app.switchForm();
            }
        }
    }
})

/* Form validator */
const submitButton = document.querySelector(".form-submit");
const inputs = document.querySelectorAll("input");
const formDisabled = document.createAttribute("disabled");
formDisabled.value = "disabled";

inputs.forEach(input => {
    input.addEventListener("input", e => {
        if(app.form.email.includes("@") && app.form.password){
            submitButton.attributes.removeNamedItem("disabled");
        }
        else{
            submitButton.attributes.setNamedItem(formDisabled);
        }
    })
})
/* /Form validator */