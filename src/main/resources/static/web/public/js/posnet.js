let app = new Vue({
    el: "#app",
    data: {
        posnet: {
            number: "",
            amount: 0,
            cvv: 0,
            description: "",
            account: ""
        }
    },
    methods: {
        makeTransaction: () => {
            axios.post("http://localhost:8080/api/posnet", app.posnet)
                .then(res => {
                    window.location.href = "http://localhost:8080/web/account2.html"
                })
        }
    }
})

//{"number": app.number, "amount": app.amount, "cvv": app.cvv, "description": app.description, "account": app.account}