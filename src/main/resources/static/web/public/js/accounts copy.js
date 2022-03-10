let app = new Vue({
    el: "#app",
    data: {
        account: {},
        blueDolar: {},
        client: {},
        dolar: {},
        email: "",
        leastExpenses: {},
        leastIncomes: {},
        loan: {},
        loans: [],
        mostExpenses: {},
        mostIncomes: {},
        oficialDolar: {},
        other: [],
        reducedTransactions: [],
        searchForm: "",
        transactions: []
    },
    created(){
            this.loadData();
            setTimeout(() => {
                console.log(app.leastIncomes)
            }, 2000);
    },
    methods: {
        changeAccount: (id) => {
            axios.get(`http://localhost:8080/api/accounts/${id}`)
                .then(res => {
                    app.account = res.data;
                    app.sortArray(app.account.transactions, "a", "id");
                    app.transactions = app.account.transactions;
                    app.other = [];
                    app.copyArray(app.account.transactions, app.other);
                    app.reducedTransactions = [];
                    app.fewTransactions(app.transactions, app.reducedTransactions);
                    app.createTable();
                })
        },
        changeDolar: () => {
            if(app.dolar == app.oficialDolar){
                app.dolar = app.blueDolar;
            }
            else{
                app.dolar = app.oficialDolar;
            }
        },
        copyArray: (array, auxArray) => {
            array.map(item => auxArray.push(item));
        },
        createTable: () => {
            const labels = [
                'Enero',
                'Febrero',
                'Marzo',
                'Abril',
                'Mayo',
                'Junio',
                'Julio',
                'Agosto',
                'Septiembre',
                'Octubre',
                'Noviembre',
                'Diciembre'
            ];
            
            const data = {
                labels: labels,
                datasets: [{
                    label: '$ Gastos por mes',
                    backgroundColor: 'transparent',
                    borderColor: 'rgb(255, 99, 99)',
                    data: []
                },{
                    label: 'Ingresos del mes',
                    backgroundColor: 'transparent',
                    borderColor: 'rgb(56, 239, 125)',
                    data: []
                }
                ]
            };

            app.sortArray(app.transactions, "a", "id");
            data.datasets[0].data = [];
            data.datasets[1].data = [];

            let debitTransactions = app.orderByMonth(app.transactions, 'DEBIT');
            debitTransactions.forEach(element => {
                data.datasets[0].data.push(element.balance);
            });
            let creditTransactions = app.orderByMonth(app.transactions, 'CREDIT');
            creditTransactions.forEach(element => {
                data.datasets[1].data.push(element.balance);
            });

            app.getChartStatistics(debitTransactions, 'mostExpenses', 'leastExpenses');
            app.getChartStatistics(creditTransactions , 'mostIncomes', 'leastIncomes');

            const config = {
                type: 'line',
                data: data,
                options: {
                    color: "#C06014"
                }
            };
            
            const myChart = new Chart(
                document.getElementById('myChart'),
                config
            );
        },
        fewTransactions: (data, auxArray) => {
            for(let i = 0; i < 3; i++){
                auxArray.push(data[i]);
            }
        },
        filter: () => {
            return app.other.filter(transaction => {
                return transaction.description.toLowerCase().includes(app.searchForm.toLowerCase())
            })
        },
        formatPrice(number){
            let balance;
            
            if(number != undefined){
                balance = number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
                return balance;
            }
        },
        getChartStatistics: (array, m, l) => {
            let most = 0;
            let least = Number.MAX_SAFE_INTEGER;
            array.forEach(element => {
                if(element.balance != 0){
                    if(element.balance > most){
                        most = element.balance;
                        app[m] = element;
                    }
                    if(element.balance < least){
                        least = element.balance;
                        app[l] = element;
                    }
                }
            })
        },
        getDolarPrice: () => {
            axios.get("https://www.dolarsi.com/api/api.php?type=valoresprincipales")
                .then(res => {
                    app.oficialDolar = res.data[0].casa;
                    app.blueDolar = res.data[1].casa;

                    app.dolar = app.oficialDolar;
                })
        },
        loadData: () => {
            const urlParams = new URLSearchParams(window.location.search);
            const myParam = urlParams.get('id');

            axios.get(`http://localhost:8080/api/clients/${myParam}`)
                .then(res => {
                    app.client = res.data;
                    app.account = res.data.accounts[0];
                    app.sortArray(app.account.transactions, "a", "id");
                    app.copyArray(app.account.transactions, app.transactions);
                    app.copyArray(app.account.transactions, app.other);
                    app.copyArray(app.client.loans, app.loans);
                    app.loan = app.client.loans[0];
                    app.fewTransactions(app.transactions, app.reducedTransactions);
                    app.getDolarPrice();
                })
                .then(res => {
                    app.createTable();
                })
                .catch(err => console.error(err))
        },
        orderByMonth: (array, type) => {
            let auxArray = [{month: "January", balance: 0}, {month: "February", balance: 0}, {month: "March", balance: 0}, {month: "April", balance: 0}, {month: "May", balance: 0}, {month: "June", balance: 0}, {month: "July", balance: 0}, {month: "August", balance: 0}, {month: "September", balance: 0}, {month: "October", balance: 0}, {month: "November", balance: 0}, {month: "December", balance: 0}];

            array.map(element => {
                if(element.type.includes(type)){
                    let month = element.date.split("");
                    month = month[5] + month[6];
                    month = parseInt(month);

                    switch(month){
                        case 1:
                                auxArray[0].balance += element.amount;
                                break;
                        case 2:
                                auxArray[1].balance += element.amount;
                                break;
                        case 3:                 
                                auxArray[2].balance += element.amount;
                                break;
                        case 4:
                                auxArray[3].balance += element.amount;
                                break;
                        case 5:
                                auxArray[4].balance += element.amount;
                                break;
                        case 6:
                                auxArray[5].balance += element.amount;
                                break;
                        case 7:
                                auxArray[6].balance += element.amount;
                                break;
                        case 8:
                                auxArray[7].balance += element.amount;
                                break;
                        case 9:                 
                                auxArray[8].balance += element.amount;
                                break;
                        case 10:
                                auxArray[9].balance += element.amount;
                                break;
                        case 11:
                                auxArray[10].balance += element.amount;
                                break;
                        case 12:
                                auxArray[11].balance += element.amount;
                                break;
                    }
                }
            })
            
            return auxArray;
        },
        search: () => {
            app.transactions = app.filter();
        },
        showSearch: () => {
            let search = document.querySelector(".search-form");
            
            app.switchDisplay(search);
        },
        // Order array as specified
        sortArray: (array, order, param) => {
            if(order == "a") array.sort((a,b) => {return a[param] - b[param]});
            if(order == "b") array.sort((a,b) => {return b[param] - a[param]});
        },
        switchDisplay: (param) => {
            if(!param.classList.contains("display-none")){
                param.classList.add("display-none");
            }
            else{
                param.classList.remove("display-none");
            }
        }
    }
})