package dto;

import utilities.DateStringUtilities;

import java.util.Objects;

public class Computer {
    public Integer id;
    public String name;
    public String dateIntroduced = "";
    public String dateDiscontinued = "";
    public Company company = Company.DEFAULT;

    public Computer(String name, String dateIntroduced, String dateDiscontinued, Company company) {
        this.name = name;
        this.dateIntroduced = dateIntroduced;
        this.dateDiscontinued = dateDiscontinued;
        this.company = company;
    }

    public Computer(){

    }

    @Override
    public String toString() {
        return "Computer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateIntroduced='" + dateIntroduced + '\'' +
                ", dateDiscontinued='" + dateDiscontinued + '\'' +
                ", company=" + company +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Computer computer = (Computer) o;
        return Objects.equals(name, computer.name) &&
                Objects.equals(dateIntroduced, computer.dateIntroduced) &&
                Objects.equals(dateDiscontinued, computer.dateDiscontinued) &&
                company == computer.company;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, dateIntroduced, dateDiscontinued, company);
    }

    /**
     * Returns a new computerDTO with dates in a format matching the home page table
     * (DD MMM yyyy)
     * Empty date strings will not be parsed
     * @return
     */
    public Computer withParsedDates() {
        Computer computer = new Computer();
        computer.name = this.name;
        if(!this.dateIntroduced.isEmpty()){
            computer.dateIntroduced = DateStringUtilities.getParsedDate(this.dateIntroduced);
        }
        if(!this.dateDiscontinued.isEmpty()){
            computer.dateDiscontinued = DateStringUtilities.getParsedDate(this.dateDiscontinued);
        }
        computer.company = this.company;
        return computer;
    }

    public enum Company{
        // Should be extended with the entire list.
        DEFAULT("-- Choose a company --", ""),
        APPLE("Apple Inc.", "1"),
        THINKING_MACHINES("Thinking Machines", "2"),
        RCA("RCA", "3"),
        NETRONICS("Netronics", "4");

        public String name;
        public String index;

        Company(String name, String index){
            this.name = name;
            this.index = index;
        }

        public static Company parseName(String name) {
            for (Company d : Company.values()) {
                if (d.name.equalsIgnoreCase(name)) {
                    return d;
                }
            }
            return null;
        }
    }
}
