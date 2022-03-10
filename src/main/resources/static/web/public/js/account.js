let app = new Vue({
    el: "#app",
    data: {
        account: {},
        transactions: []
    },
    created(){
        this.loadData();
    },
    methods: {
        formatDate: (param, from, to) => {
            return param.slice(from, to);
        },
        formatPrice: (number) => {
            number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
        },
        loadData: () => {
            axios.get(`http://localhost:8080/api/client/current`)
                .then(res => {
                    app.account = res.data;
                    console.log(app.account.transactions)
                    app.sortArray(app.account.transactions, "a", "id");
                    app.transactions = res.data.transactions;
                })
                .catch(err => {
                    console.error(err);
                })
        },
        logout: () => {
            axios.post("/api/logout")
                .then(res => {
                    console.log("Signed out!");
                    document.location.href = '/web/index.html';
                })
                .catch(err => {
                    console.error(err);
                })
        },
        // Order array as specified
        sortArray: (array, order, param) => {
            if(order == "a") array.sort((a,b) => {return a[param] - b[param]});
            if(order == "b") array.sort((a,b) => {return b[param] - a[param]});
        }
    },
})

let app2 = new Vue({
    el: "#app2",
    data: {
        client: {}
    }
})