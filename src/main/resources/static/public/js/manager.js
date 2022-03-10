let app = new Vue({
    el: "#app",
    data: {
        clients: [],
        json: [],
        form: {
            firstName: "",
            lastName: "",
            email: ""
        }
    },
    methods: {
        loadData(){
            axios.get("http://localhost:8080/clients")
                .then(res =>{
                    let data = res.data._embedded.clients;

                    this.json = res.data;
                    this.clients = data;
                })
        },
        addClient(){
            if(this.form.firstName && this.form.lastName && this.form.email.includes("@")){
                let client = {
                    firstName: this.form.firstName,
                    lastName: this.form.lastName,
                    email: this.form.email
                };

                this.postClient(client);
            }
        },
        postClient(client){
            axios.post("http://localhost:8080/clients", client)
                .then(res => {
                    alert("Añadió al cliente con éxito");
                    this.loadData();
                })
        },
        deleteClient(client){
            axios.delete(client)
                .then(res => {
                    alert("Eliminó al cliente con éxito");
                    this.loadData();
                })
        }
    },
    mounted(){
        this.loadData();
    }
})