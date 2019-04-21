package dto;

public class Computer {
    public String name;
    public String dateIntroduced;
    public String dateDiscontinued;
    public Company company;

    public enum Company{
        APPLE("Apple Inc."),
        THINKING_MACHINES("Thinking Machines"),
        RCA("RCA"),
        NETRONICS("Netronics");

        public String name;

        Company(String name){
            this.name = name;
        }
    }
}
