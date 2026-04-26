package RestAssured;

public class UserPOJO {
    private String name;
    private String email;
    private String role;
    private Address address;
    private String id;

    UserPOJO(){
    }

    UserPOJO(String name, String email, String role, Address address){
        this.name = name;
        this.email = email;
        this.role = role;
        this.address= address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Address{
        private String city;

        public String getZip() {
            return zip;
        }

        public void setZip(String zip) {
            this.zip = zip;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        private String zip;

        public Address(){
        }

        public Address(String city, String zip){
            this.city = city;
            this.zip = zip;
        }


    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }
}