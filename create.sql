create database scs_jdbc;
use scs_jdbc;

create table IF NOT EXISTS counselling_sessions 
(sessionid integer not null auto_increment, 
date date, 
feedback varchar(255), 
from_time time, 
to_time time, 
counseled_by_id integer, 
counsels_to_id integer, 
primary key (sessionid));

create table IF NOT EXISTS counselor 
(counselor_id integer not null auto_increment, 
current_status varchar(255), 
date_of_joining varchar(255), 
designation varchar(255), 
email_id varchar(255), 
job_type varchar(255), 
name varchar(255), 
phone_no bigint, 
qualification varchar(255), 
start_time time,
end_time time, 
primary key (counselor_id));

alter table counselling_sessions add foreign key (counseled_by_id) references counselor (counselor_id);
alter table counselling_sessions add foreign key (counsels_to_id) references student (student_roll_no);

create table IF NOT EXISTS event 
(eventid integer not null auto_increment, 
event_name varchar(255),
description text,
date date, 
from_time time, 
minutesof_meeting varchar(255), 
no_of_attendees integer, 
resources varchar(255), 
to_time time, 
seekpermission_id integer, 
venued_at_id integer, 
primary key (eventid));

create table IF NOT EXISTS faculty 
(faculty_id integer not null auto_increment, 
department varchar(255), 
email_id varchar(255), 
name varchar(255), 
phone_no bigint, 
qualification varchar(255), 
primary key (faculty_id));

create table IF NOT EXISTS inititatives 
(initiatives_id integer not null auto_increment,
description text,
minutes_of_meeting varchar(255), 
no_of_attendees bigint, 
resources varchar(255), 
start_date date, 
timings time, 
title varchar(255), 
venued_at_id integer, 
permissionforinitiative_id integer, 
primary key (initiatives_id));

create table IF NOT EXISTS scsmembers 
(member_id integer not null auto_increment, 
current_position varchar(255), 
from_date date, 
to_date date, 
membersresponsblebills_id integer, 
verticalmemberbelongs_id integer, 
primary key (member_id));

create table IF NOT EXISTS stud_participate_event 
(student_student_roll_no integer not null, 
event_eventid integer not null, 
role varchar(255),
feedback varchar(255),
primary key (student_student_roll_no, event_eventid));

create table IF NOT EXISTS stud_participate_initiative 
(student_student_roll_no integer not null, 
inititatives_initiatives_id integer not null, 
role varchar(255),
feedback varchar(255),
primary key (student_student_roll_no, inititatives_initiatives_id));

alter table stud_participate_initiative add foreign key (inititatives_initiatives_id) references inititatives (initiatives_id);
alter table stud_participate_initiative add foreign key (student_student_roll_no) references student (student_roll_no);

alter table stud_participate_event add foreign key (event_eventid) references event (eventid);
alter table stud_participate_event add foreign key (student_student_roll_no) references student (student_roll_no);

create table IF NOT EXISTS student 
(student_roll_no integer not null auto_increment, 
date_of_birth date, 
department varchar(255), 
e_mail_id varchar(255), 
name varchar(255), 
phone_number bigint, 
program varchar(255), 
year_of_gradn integer, 
year_ofjoin integer, 
scsmembership_id integer, 
student_passed_out_id integer, 
    group_id integer,
address varchar(255),
primary key (student_roll_no));

alter table student add foreign key (scsmembership_id) references scsmembers (member_id);
alter table student add foreign key (group_id) references imgroups (groupid);

create table IF NOT EXISTS user (
user_id bigint not null auto_increment, 
email varchar(45) unique, 
enabled bit default 1, 
full_name varchar(20), 
role varchar(20),
vertical varchar(255),
password varchar(64), 
roll_no varchar(10),
stud_id int,
fac_id int,
reset_password_token varchar(30),
primary key (user_id),
FOREIGN KEY (stud_id) REFERENCES student(student_roll_no) ON DELETE CASCADE,
FOREIGN KEY (fac_id) REFERENCES faculty(faculty_id) ON DELETE CASCADE);


create table IF NOT EXISTS bills (
    billid integer not null auto_increment,
    title text,
    date_of_billing date,
    purpose text,
    status boolean not null default 0,
    member_id int,
    FOREIGN KEY (member_id) REFERENCES scsmembers(member_id) ON DELETE CASCADE,
    amount int,
    primary key (billid)
);

create table IF NOT EXISTS notifications(
    notification_id integer not null auto_increment,
    type varchar(255),
    url_id varchar(255),
    time DATETIME,
    primary key (notification_id)
);

# <<<<<<< HEAD
create table imgroups 
(groupid integer not null auto_increment, 
induction_year integer, 
supervised_by integer, 
primary key (groupid));

create table induction_mentor 
(mentorid integer not null auto_increment, 
im_grp_id integer, 
year integer, 
member_id integer,
primary key (mentorid)) ;

create table induction_program 
(year integer not null auto_increment, 
chairman varchar(255), 
duration varchar(255), 
no_of_attendees integer, 
primary key (year));


alter table imgroups add foreign key (induction_year) references induction_program (year);
alter table imgroups add foreign key (supervised_by) references faculty (faculty_id);

alter table induction_mentor add foreign key (im_grp_id) references imgroups (groupid);
alter table induction_mentor add foreign key (member_id) references scsmembers (member_id);
alter table induction_mentor add foreign key (year) references induction_program (year);


# =======
create table IF NOT EXISTS unread(
    unread_id integer not null auto_increment,
    user_id bigint,
    FOREIGN KEY (user_id) REFERENCES user(user_id) ON DELETE CASCADE,
    notification_id integer,
    FOREIGN KEY (notification_id) REFERENCES notifications(notification_id),
    primary key (unread_id)
);

create table IF NOT EXISTS team_meetings (
    meeting_id integer not null auto_increment,
    date date,
    from_time time,
    meeting_title varchar(255),
    meeting_description text,
    location varchar(255),
    to_time time,
    organiser_id INT,
    FOREIGN KEY (organiser_id) REFERENCES scsmembers(member_id),
    primary key (meeting_id)
);

create table IF NOT EXISTS vertical (
    vertical_id integer not null auto_increment,
    name_of_vertical varchar(255),
    primary key (vertical_id)
);

create table IF NOT EXISTS monthly_reports (
    report_id integer not null auto_increment,
    title text,
    compiled_on date,
    from_date date,
    report_description text,
    to_date date,
    student_compiles_reports_id integer,
    status boolean not null default 0,
    FOREIGN KEY (student_compiles_reports_id) REFERENCES scsmembers(member_id),
    primary key (report_id)
);


insert into vertical(name_of_vertical) values('WELLNESS');
insert into vertical(name_of_vertical) values('ACADEMICS');
insert into vertical(name_of_vertical) values('CAREER');
insert into vertical(name_of_vertical) values('SKILLS');
insert into vertical(name_of_vertical) values('INTERNATIONAL_EXCHANGE');

alter table scsmembers add foreign key (verticalmemberbelongs_id) references vertical (vertical_id);


create table IF NOT EXISTS application(
    application_id bigint not null auto_increment,
    date_of_application date,
    status boolean,
    vertical_id int,
    stud_id int,
    program varchar(255),
    department varchar(255),
    position varchar(255),
    list_of_pors text,
    commitment int,
    skills text,
    FOREIGN KEY (stud_id) REFERENCES student(student_roll_no),
    FOREIGN KEY (vertical_id) REFERENCES vertical(vertical_id),
    primary key (application_id)
);

create table venue 
(venueid integer not null auto_increment, 
capacity bigint, 
contactnumber bigint, 
contactperson varchar(255), 
location varchar(255), 
venue_name varchar(255), 
primary key (venueid));

alter table event add foreign key (venued_at_id) references venue (venueid);
alter table inititatives add foreign key (initiatvehostedat_id) references venue (venueid);





	
