// cudb.c

#include <stdio.h>
#include <string.h>


#define MIN_YEAR 2009
#define MAX_YEAR 2040
#define NAME_SIZE 5
#define DATABASE_SIZE 10000
#define DATA_BITS 13
#define COMMAND_MSG "\n" "0: Halt\n" "1: List all students\n" "2: Add a new student\n" "3: Test database"

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

void databaseTest();
void addStudent( database_t* database, student_t student );
void printStudent( student_t* student );
void listStudents( database_t* database );

int main( void ) {
	int choise;
	int exit = 0;
	database_t database;
	database.students = 0;

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
			fflush( stdin ); //Is this necessary?
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
				fflush( stdin );

				printf( "Year, 2009-2040: " );
				fflush( stdout );
				if ( scanf( "%d", &year ) <= 0 )
					error = 1;
				fflush( stdin );

				printf( "Semester, 0/1: " );
				fflush( stdout );
				if ( scanf( "%d", &semester ) <= 0 )
					error = 1;
				fflush( stdin );

				printf( "Grade, 0-255: " );
				fflush( stdout );
				if ( scanf( "%d", &grade ) <= 0 )
					error = 1;
				fflush( stdin );

				if ( !error )
					addStudent( &database, encodeStudent( name, year, semester, grade ) );
				else
					puts( "Error in entered data" );
				break;
			case 3:
				databaseTest();
				break;
			default:
				puts( "Unknown command. Try:\n" COMMAND_MSG );
			}
		} else {
			puts( "Only numbers are accepted" );
			fflush( stdin ); //Prevent looping
		}
	}

	puts( "Bye!" );
	return 0;
}

void addStudent( database_t* database, student_t student ){
	if ( database->students < DATABASE_SIZE - 1 ) {
		if ( student.data & ( 0x1 << 15 ) ) {
			printf( "ERROR! Student data is illegal! " );
			printStudent( &student );
		} else {
			printf( "Adding student: " );
			printStudent( &student );
			database->student[database->students] = student;
			database->students++;
		}
	} else {
		puts( "Database is full, contact your local SYS-admin (or don't)" );
	}
}

void listStudents( database_t* database ){
	int i;
	double average = 0;
	for ( i = 0; i < database->students; i++ ) {
		printf( "s%04d ", i );
		printStudent( &(database->student[i]) );
		//average += decodeSudentGrade( &(database->student[i]) );
	}
	average /= database->students;
	//printf( "\n Average grade: %d", average );
}

char* decodeStudentName( student_t* student ){
	return student->name;
}

int decodeStudentYear( student_t* student ){
	return ( student->data & 0x1F ) + MIN_YEAR;
}

int decodeStudentSemester( student_t* student ){
	return ( student->data >> 5 ) & 0x1;
}

int decodeStudentGrade( student_t* student ){
	return ( student->data >> 6 ) & 0xFF;
}

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

void databaseTest( void ){
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
}

void printStudent( student_t* student ){
	int parity = 0;
	int i;
	for ( i = 0; i < DATA_BITS; i++ )
		parity ^= ( student->data << i ) & 0x1;
	if ( ( ( student->data >> 14 ) & 0x1 ) ^ parity ) {
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
