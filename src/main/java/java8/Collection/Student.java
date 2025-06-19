package java8.Collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
class Student implements Comparable<Student> {
    String firstname; String lastname; byte age;
    @Override
    public int compareTo(Student o) { return this.age > o.age ? 1 : (this.age == o.age ? 0 : -1) ;}
    @Override
    public String toString(){  return this.firstname+this.lastname+this.age; }
}