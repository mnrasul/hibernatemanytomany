Problem: How to project columns across two entities of a many to many relation

Example:
Employee (id, firstname, lastname)
Meeting (id, subject, meetingDate)

Suppose we have
Employee Data
1 , A, AA
2 , B, BB

Meeting
1, AB Meeting, X
2, Another meeting, X
3, B only

Employee_Meeting
AB Meeting attended by A and B
Another meeting attended by A
B only meeting attended by B
 
1, 1
2, 1
1, 2
2, 3

Now we want a flattened listing of firstname, meeting subject

   Criteria criteria = session.createCriteria(Employee.class, "e");
        criteria.setFetchMode("e.meetings", FetchMode.JOIN);
        criteria = criteria.createAlias("e.meetings", "m", JoinType.LEFT_OUTER_JOIN);
        final ProjectionList projections = Projections.projectionList()
                .add(Projections.property("e.firstname"))
                .add(Projections.property("m.subject"))
                ;
        criteria.setProjection(projections);
        final List<Object> list = criteria.list();

I want the result to have
A, AB Meeting
A, Another meeting
B, AB Meeting
B, B Only

If I do not include any projections, I get the Employee object with corresponding meeting list filled appropriately.

Once, I add projections, I only get Employee related fields.

=======
run in memory from maven

- run the db engine
mvn exec:java -Dexec.mainClass="org.hsqldb.Server" -Dexec.args="-database.0 mem:hibdata"


- run the db UI
mvn exec:java -Dexec.mainClass="org.hsqldb.util.DatabaseManagerSwing"


kill a process on a specific port
 lsof -P | grep ':8080' | awk '{print $2}' | xargs kill -9