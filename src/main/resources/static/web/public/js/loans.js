let app = new Vue({
    el: "#app",
    data: {
        accounts: [],
        amount: 0,
        client: {},
        id: 0,
        interests: 0,
        loan: {},
        loan_app: {
            id: 0,
            amount: 0,
            payments: 0,
            destinyAccount: "",
            interest: 0
        },
        loans: []
    },
    created(){
        this.loadData();
        this.getLoans();
    },
    methods: {
        amountControl: () => {
            if(app.loan_app.amount > app.loan.maxAmount){
                app.loan_app.amount = app.loan.maxAmount;
            }
        },
        calculateInterests: () => {
            let amount = parseInt(app.loan_app.amount);
            let payment = parseInt(app.loan_app.payments);
            let amountAux = 0;
            let paymentAux = 0;
            let totalAux = 0;

            // Estimate interests based on amount required
            if(amount <= 100000) amountAux = 1;
            else if(amount > 100000 && amount <= 200000) amountAux = 2;
            else if(amount > 200000 && amount <= 300000) amountAux = 3;
            else if(amount > 300000) amountAux = 4;

            // Estimate interests based on payments required
            if(payment == 6) paymentAux = 1;
            if(payment == 12) paymentAux = 1;
            if(payment == 24) paymentAux = 2;
            if(payment == 36) paymentAux = 3;
            if(payment > 36) paymentAux = 4;
            
            totalAux = amountAux + paymentAux;

            // Select interest according to amount+payments
            if(totalAux <= 3) app.interests = app.loan.interests[0];
            if(totalAux > 3 && totalAux <= 5) app.interests = app.loan.interests[1];
            if(totalAux > 5 && totalAux <= 7) app.interests = app.loan.interests[2];
            if(totalAux > 7) app.interests = app.loan.interests[3];
            
        },
        changeDisplay: () => {
            let content = document.querySelector(".content");
            let loan_preview= document.querySelector(".loan-preview")
            let loan_application = document.querySelector(".loan-application");
            let card_btn = document.querySelector(".card-btn");

            if(!content.classList.contains("display-none")){
                content.classList.add("animate__fadeOutUp");

                setTimeout(() => {
                    content.classList.add("display-none");
                    loan_application.classList.remove("display-none");
                    loan_preview.classList.remove("display-none");
                }, 600);
            }

            if(app.loan_app.destinyAccount != ""){
                card_btn.classList.remove("display-none");
            }
        },
        changeLoan: () => {
            app.loan = app.loans[app.id - 1];
            app.loan_app.id = app.id;
        },
        formatPrice: (number) => {
            if(number != undefined){
                let balance = number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
                return balance;
            }
        },
        getLoan: () => {
            app.loan_app.amount = parseFloat(app.amount) + ((app.amount * app.interests) / 100);
            app.loan_app.interest = app.interests;

            axios.post("/api/loans", app.loan_app)
                .then(res => {
                    window.location.href = "/web/account2.html";
                })
        },
        getLoans: () => {
            axios.get("/api/loans")
                .then(res => {
                    app.loans = res.data;
                    app.sortArray(app.loans, "a", "id");
                })
        },
        loadData: () => {
            axios.get(`/api/clients/current`)
                .then(res => {
                    app.client = res.data;
                    app.accounts = [];
                    app.client.accounts.forEach(account => {
                        if(account.active == true){
                            app.accounts.push(account);
                        }
                    })
                })
                .catch(err => {
                    console.error(err);
                })
        },
        sortArray: (array, order, param) => {
            if(order == "a") array.sort((a,b) => {return a[param] - b[param]});
            if(order == "b") array.sort((a,b) => {return b[param] - a[param]});
        }
    }
})