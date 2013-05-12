// cudb.c

#include <stdio.h>
#include <string.h>


#define MIN_YEAR 2009
#define MAX_YEAR 2040
#define NAME_SIZE 5
#define DATABASE_SIZE 10000
#define DATA_BITS 13
#define COMMAND_MSG "\n" "0: Halt\n" "1: List all students\n" "2: Add a new student\n" "3: Test database\n" "4: Corrupt student data >:) (use only for testing)"

typedef struct {
	char name[NAME_SIZE];
	int data; //0-4 year, 5 semester, 6-13 grade, 14 parity, 15 fault
} student_t;

typedef struct {
	int students;
	student_t student[DATABASE_SIZE];
} database_t;

student_t encodeStudent( char* name, int year, int semester, int grade );
char* decodeStudentName( student_t* student );
int decodeStudentYear( student_t* student );
int decodeStudentSemester( student_t* student );
int decodeStudentGrade( student_t* student );
int decodeStudentParity( student_t* student );

void databaseTest();
void addStudent( database_t* database, student_t student );
void printStudent( student_t* student );
void listStudents( database_t* database );

int main( void ) {
	int choise;
	int exit = 0;
	database_t database;
	database.students = 0;

	int id;
	int year;
	int grade;
	int semester;
	int error;
	char name[NAME_SIZE];

	puts( "Welcome to CUDB - The C University Data Base\n"
			COMMAND_MSG );

	while( !exit ){
		puts( "\nEnter command: " );
		fflush( stdout );
		if ( scanf( "%d", &choise ) > 0 ) {
			rewind( stdin ); //Is this necessary?
			switch ( choise ) {
			case 0:
				exit = 1;
				break;
			case 1:
				listStudents( &database );
				break;
			case 2:
				error = 0;
				printf( "Name, 4 chars: " );
				fflush( stdout );
				if ( scanf( "%4s", name ) <= 0 )
					error = 1;
				rewind( stdin );

				printf( "Year, 2009-2040: " );
				fflush( stdout );
				if ( scanf( "%d", &year ) <= 0 )
					error = 1;
				rewind( stdin );

				printf( "Semester, 0/1: " );
				fflush( stdout );
				if ( scanf( "%d", &semester ) <= 0 )
					error = 1;
				rewind( stdin );

				printf( "Grade, 0-255: " );
				fflush( stdout );
				if ( scanf( "%d", &grade ) <= 0 )
					error = 1;
				rewind( stdin );

				if ( !error )
					addStudent( &database, encodeStudent( name, year, semester, grade ) );
				else
					puts( "Error in entered data" );
				break;
			case 3:
				databaseTest();
				break;
			case 4:
				printf( "Student id to corrupt: " );
				fflush( stdout );
				if ( scanf( "%d", &id ) > 0 )
					if ( ( id >= 0 ) || ( id < DATABASE_SIZE ) ) {
						printf( "Before: ");
						printStudent( &(database.student[id]) );
						database.student[id].data ^= 0x1;
						printf( "After: ");
						printStudent( &(database.student[id]) );
					}
				rewind( stdin );

				break;
			default:
				puts( "Unknown command. Try:\n" COMMAND_MSG );
			}
		} else {
			puts( "Only numbers are accepted" );
			rewind( stdin ); //Prevent looping
		}
	}

	puts( "Bye!" );
	return 0;
}

/**
 * Function for adding a student to the database.
 * The student is only added, if the students data "error" flag is not asserted
 * and there is room in the database.
 */
void addStudent( database_t* database, student_t student ){
	if ( database->students < DATABASE_SIZE ) {
		if ( student.data & ( 0x1 << 15 ) ) {
			printf( "ERROR! Student data is illegal! " );
			printStudent( &student );
		} else {
			printf( "Adding student: s%04d ", database->students );
			printStudent( &student );
			database->student[database->students] = student;
			database->students++;
		}
	} else {
		puts( "Database is full, contact your local SYS-admin (or don't)" );
	}
}

/**
 * Lists all students and calculates the average for all non-corrupt data points
 */
void listStudents( database_t* database ){
	int i;
	int faults = 0;
	double average = 0;
	for ( i = 0; i < database->students; i++ ) {
		printf( "s%04d ", i ); //Id
		printStudent( &(database->student[i]) ); //name, year, semester, grade
		if ( decodeStudentParity( &(database->student[i]) ) ) //if corrupt, do not use for average
			faults++;
		else
			average += (double) decodeStudentGrade( &(database->student[i]) );
	}
	average /= database->students - faults;
	printf( "\nAverage grade: %3.2f\n", average );
}

/**
 * Returns a pointer to the name of a given student
 */
char* decodeStudentName( student_t* student ){
	return student->name;
}

/**
 * Returns the starting year for a given student.
 */
int decodeStudentYear( student_t* student ){
	return ( student->data & 0x1F ) + MIN_YEAR;
}

/**
 * Returns the starting semester for a given student. 0 for autumn 1 for spring
 */
int decodeStudentSemester( student_t* student ){
	return ( student->data >> 5 ) & 0x1;
}

/**
 * Returns the gade of a student
 */
int decodeStudentGrade( student_t* student ){
	return ( student->data >> 6 ) & 0xFF;
}

/**
 * Returns 1 of there were problems with data parity. 0 if no problems
 */
int decodeStudentParity( student_t* student ){
	int parity = 0;
	int i;
	for ( i = 0; i < DATA_BITS; i++ )
		parity ^= ( student->data << i ) & 0x1;
	return ( ( student->data >> 14 ) & 0x1 ) ^ parity;
}

/**
 * Encode student data for space saving. If the entered data does not "fit",
 * that field is not set and the error flag is asserted
 */
student_t encodeStudent( char* name, int year, int semester, int grade ){
	int i;
	int parity = 0;
	student_t student;
	for ( i = 0; i < NAME_SIZE; i++ )
		student.name[i] = '\0';
	student.data = 0;
	//check if all inputs are valid
	//Name length is to large enough
	if ( strlen( name ) >= NAME_SIZE ) {
		student.data |= 1 << 15;
	} else {
		for ( i = 0; i < strlen( name ); i++ )
			student.name[i] = name[i];
		student.name[4] = '\0';
	}
	//Year is out of bounds
	if ( ( year > MAX_YEAR ) || ( year < MIN_YEAR ) ) {
		student.data |= 1 << 15;
	} else {
		student.data |= ( ( year - MIN_YEAR ) & 0x1F );
	}
	//Semester is out of bounds
	if ( ( semester > 1 ) || ( semester < 0 ) )  {
		student.data |= 1 << 15;
	} else {
		student.data |= ( semester & 0x1 ) << 5;
	}
	//Grade is out of bounds
	if ( ( grade > 0xFF ) || ( grade < 0 ) ) {
		student.data |= 1 << 15;
	} else {
		student.data |= ( grade & 0xFF ) << 6;
	}
	//Encode parity.
	for ( i = 0; i < DATA_BITS; i++ )
		parity ^= ( student.data << i ) & 0x1;
	student.data |= parity << 14;

	return student;
}

/**
 * Tests different inputs to the database, but not the terminal/UI
 */
void databaseTest( void ){
	int i;
	database_t database;
	database.students = 0;
	puts( "Initializing test:" );
	puts( "( \"JOH0\", 2009, 0, 0 )" );
	addStudent( &database, encodeStudent( "JOH0", 2009, 0, 0 ) );
	puts( "( \"JOH1\", 2008, 0, 0 )" );
	addStudent( &database, encodeStudent( "JOH1", 2008, 0, 0 ) );
	puts( "( \"JOH2\", 2040, 0, 0 )" );
	addStudent( &database, encodeStudent( "JOH2", 2040, 0, 0 ) );
	puts( "( \"JOH3\", 2041, 0, 0 )" );
	addStudent( &database, encodeStudent( "JOH3", 2041, 0, 0 ) );
	puts( "( \"JOH4\", 2009, 1, 0 )" );
	addStudent( &database, encodeStudent( "JOH4", 2009, 1, 0 ) );
	puts( "( \"JOH5\", 2009, 2, 0 )" );
	addStudent( &database, encodeStudent( "JOH5", 2009, 2, 0 ) );
	puts( "( \"JOH6\", 2009, -1, 0 )" );
	addStudent( &database, encodeStudent( "JOH6", 2009, -1, 0 ) );
	puts( "( \"JOH7\", 2009, 0, 255 )" );
	addStudent( &database, encodeStudent( "JOH7", 2009, 0, 255 ) );
	puts( "( \"JOH8\", 2009, 0, 256 )" );
	addStudent( &database, encodeStudent( "JOH8", 2009, 0, 256 ) );
	puts( "( \"JOH9\", 2009, 0, -1 )" );
	addStudent( &database, encodeStudent( "JOH9", 2009, 0, -1 ) );
	puts( "( \"JOH10\", 2009, 0, 0 )" );
	addStudent( &database, encodeStudent( "JOH10", 2009, 0, 0 ) );
	puts( "( \"J11\", 2009, 0, 0 )" );
	addStudent( &database, encodeStudent( "J11", 2009, 0, 0 ) );
	puts( "( \"J\\0 12abc\", 2009, 0, 0 )" );
	addStudent( &database, encodeStudent( "J\0 12abc", 2009, 0, 0 ) );
	puts( "( \"J\\nOH\", 2009, 0, 0 )" );
	addStudent( &database, encodeStudent( "J\nOH", 2009, 0, 0 ) );
	listStudents( &database );
	puts( "Write something to do test of filled database" );
	fflush ( stdout );
	scanf( "%d", &i );
	rewind( stdin );
	puts( "Adding 10000 students" );
	for ( i = 0; i < DATABASE_SIZE; i++ )
		addStudent( &database, encodeStudent( "Many", 2009, 0, 128 ) );

}

/**
 * Prints information about a particular student. If there are parity problems,
 * this will be displayed.
 */
void printStudent( student_t* student ){
	if ( decodeStudentParity( student ) ) {
		printf( "ERROR! DATA IS CORRUPTED!!\n" );
	} else {
		printf( "%s ", decodeStudentName( student ) );
		printf( "%4d ", decodeStudentYear( student ) );
		if ( decodeStudentSemester( student ) )
			printf( "Spring " );
		else
			printf( "Autumn " );
		printf( "%03d\n", decodeStudentGrade( student ) );
	}
}
