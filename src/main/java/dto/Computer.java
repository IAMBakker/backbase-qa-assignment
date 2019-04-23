package dto;

import java.util.Objects;

public class Computer {
    public Integer id;
    public String name;
    public String dateIntroduced;
    public String dateDiscontinued;
    public Company company = Company.DEFAULT;

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

    public enum Company{
        DEFAULT("-- Choose a company --"),
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
