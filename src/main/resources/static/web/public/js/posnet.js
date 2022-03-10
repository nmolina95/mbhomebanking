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
            axios.post("/api/posnet", app.posnet)
                .then(res => {
                    window.location.href = "/web/account2.html"
                })
        }
    }
})

//{"number": app.number, "amount": app.amount, "cvv": app.cvv, "description": app.description, "account": app.account}