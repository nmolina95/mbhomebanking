let app = new Vue({
    el: "#app",
    data: {
        account: {},
        accountCancel: 0,
        accountType: "",
        accounts: [],
        loansPlans: [],
        blueDolar: {},
        client: {},
        creditCards: [],
        debitCards: [],
        dolar: {},
        form: {
            color: "",
            type: ""
        },
        email: "",
        leastExpenses: {},
        leastIncomes: {},
        loan: {},
        loans: [],
        mostExpenses: {},
        mostIncomes: {},
        oficialDolar: {},
        other: [],
        receiverType: 0,
        reducedTransactions: [],
        searchForm: "",
        transactions: [],
        transaction: {
            amount: null,
            description: "",
            sender: "",
            receiver: ""
        }
    },
    created(){
            this.loadData();
    },
    methods: {
        cancelAccount: () => {
            axios.patch(`/api/clients/current/accounts/delete/${app.accountCancel}`)
                .then(res => {
                    window.location.href = "/web/account2.html";
                })
        },
        cancelCard: (id) => {
            Swal.fire({
                title: 'Are you sure?',
                text: "You won't be able to revert this!",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#d33',
                cancelButtonColor: 'rgba(32, 190, 128, 1)',
                confirmButtonText: 'Yes, cancel it!'
              }).then((result) => {
                if (result.isConfirmed) {
                  Swal.fire(
                    'Deleted!',
                    'Your card has been canceled.',
                    'success'
                  )

                  setTimeout(() => {
                    axios.patch(`/api/clients/current/cards/${id}`)
                        .then(res => {
                            window.location.href = "/web/cards.html";
                        })
                  }, 1500);
                }
            })

        },
        changeAccount: (id) => {
            axios.get(`/api/accounts/${id}`)
                .then(res => {
                    app.account = res.data;
                    app.sortArray(app.account.transactions, "b", "id");
                    app.transactions = app.account.transactions;
                    app.other = [];
                    app.copyArray(app.account.transactions, app.other);
                    app.reducedTransactions = [];
                    app.fewTransactions(app.transactions, app.reducedTransactions);
                    app.createTable();
                })
        },
        changeDisplay: () => {
            let start = document.querySelector(".new-card-info");
            let formContainer = document.querySelector(".form-container");
            let color = document.querySelector(".color-fieldset");
            let type = document.querySelector(".type-fieldset");
            let cardBtn = document.querySelector(".card-btn");
            
            if(!start.classList.contains("display-none")){
                start.classList.add("display-none");
                formContainer.classList.remove("display-none");
                color.classList.remove("display-none");
            }
            
            if(app.form.color != ""){
                type.classList.remove("display-none");
            }

            if(app.form.type != ""){
                cardBtn.classList.remove("display-none");
            }
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
        copyCVV: (cvv) => {
            navigator.clipboard.writeText(cvv);

            const Toast = Swal.mixin({
                toast: true,
                position: 'center',
                showConfirmButton: false,
                timer: 1400,
                timerProgressBar: false,
                /*didOpen: (toast) => {
                  toast.addEventListener('mouseenter', Swal.stopTimer)
                  toast.addEventListener('mouseleave', Swal.resumeTimer)
                }*/
            })
            
            Toast.fire({
            icon: 'success',
            title: 'CVV Copied!'
            })
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
                    label: 'Monthly expendings',
                    backgroundColor: 'rgba(175, 217, 184,0.3)',
                    //backgroundColor: 'rgba(252, 79, 79, 0.3)',
                    backgroundColor: 'transparent',
                    borderColor: '#F3F4ED',
                    borderColor: 'rgba(32, 190, 128, 0.9)',
                    borderColor: 'rgba(252,79,79,0.9)',
                    data: []
                },{
                    label: 'Monthly incomes',
                    backgroundColor: 'rgba(242, 243, 236, 0.5)',
                    backgroundColor: 'transparent',
                    borderColor: '#b0dab9',
                    borderColor: '#009C86',
                    data: []
                }
                ]
            };

            app.sortArray(app.transactions, "b", "id");
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
        filterArray: (array, param, compare) => {
            return array.filter(element => {
                return element[param] == compare;
            })
        },
        formatDate: (param, from, to) => {
            return param.slice(from, to);
        },
        formatPrice(number){
            if(number != undefined){
                let balance = number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".");
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
        getCard: () => {
            let card = {
                color: app.form.color,
                type: app.form.type
            }

            axios.post("/api/clients/current/cards", `color=${card.color}&type=${card.type}`)
                .then(res => {
                    window.location.href = "/web/cards.html";
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
        getNewAccount: () => {
            axios.post("/api/clients/current/accounts", `accountType=${app.accountType}`)
                .then(res => {
                    window.location.reload();
                })
        },
        loadData: () => {
            axios.get(`/api/clients/current`)
                .then(res => {
                    app.client = res.data;
                    app.sortArray(app.client.accounts, "a", "id");
                    
                    app.accounts = [];
                    app.client.accounts.forEach(account => {
                        if(account.active == true){
                            app.accounts.push(account);
                        }
                    })

                    if(app.client.cards){
                        //app.creditCards = app.filterArray(app.client.cards, "type", "CREDIT");
                        //app.debitCards = app.filterArray(app.client.cards, "type", "DEBIT");

                        app.filterArray(app.client.cards, "type", "CREDIT").forEach(account => {
                            if(account.active == true){
                                app.creditCards.push(account);
                            }
                        })

                        app.filterArray(app.client.cards, "type", "DEBIT").forEach(account => {
                            if(account.active == true){
                                app.debitCards.push(account);
                            }
                        })
                    }
                    
                    if(app.accounts){
                        app.account = app.accounts[0];
                        app.sortArray(app.account.transactions, "b", "id");
                        app.copyArray(app.account.transactions, app.transactions);
                        app.copyArray(app.account.transactions, app.other);
                        app.fewTransactions(app.transactions, app.reducedTransactions);
                    }
                    
                    if(app.client.loans){
                        app.copyArray(app.client.loans, app.loans);
                        app.sortArray(app.loans, "a", "id");
                        app.loan = app.client.loans[0];
                    }
                    
                    app.getDolarPrice();
                })
                .then(res => {
                    if(app.account.transactions){
                        app.createTable();
                    }
                })
                .catch(err => console.error(err))
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
        makeTransaction: () => {
            let confirm = window.confirm("Confirm transfer");

            if(confirm){
                axios.post("/api/transactions",`amount=${app.transaction.amount}&description=${app.transaction.description}&sender=${app.transaction.sender}&receiver=${app.transaction.receiver}`)
                .then(res => {
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'center',
                        showConfirmButton: false,
                        timer: 1400,
                        timerProgressBar: false,
                        /*didOpen: (toast) => {
                          toast.addEventListener('mouseenter', Swal.stopTimer)
                          toast.addEventListener('mouseleave', Swal.resumeTimer)
                        }*/
                      })
                      
                      Toast.fire({
                        icon: 'success',
                        title: 'Successful transaction'
                      })
                })
                .then(() => {
                    setTimeout(() => {
                        app.sortArray(app.transactions, "b", "id");
                        window.location.href = "/web/account2.html";
                    }, 1400);
                })
                .catch(err => {
                    console.error(err);
                    const Toast = Swal.mixin({
                        toast: true,
                        position: 'center',
                        showConfirmButton: false,
                        timer: 1400,
                        timerProgressBar: false
                      })
                      
                      Toast.fire({
                        icon: 'error',
                        title: 'Error in transaction'
                      })
                })
            }
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
            let effect = "animate__fadeOutUp";

            //search.classList.toggle(effect);

            if(!search.classList.contains("display-none")){
                search.classList.toggle(effect);
                setTimeout(() => {
                    app.switchDisplay(search);
                    search.classList.toggle(effect);
                }, 800);
            }
            else{
                app.switchDisplay(search);
            }
            
        },
        // Order array as specified
        sortArray: (array, order, param) => {
            if(order == "a") array.sort((a,b) => {return a[param] - b[param]});
            if(order == "b") array.sort((a,b) => {return b[param] - a[param]});
        },
        switchDisplay: (param) => {
            !param.classList.contains("display-none") ? param.classList.add("display-none") : param.classList.remove("display-none");
        }
    }
})