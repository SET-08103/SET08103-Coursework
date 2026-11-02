# USE CASE: 5 Produce a Report on the Top N Populated Countries in a Continent

## CHARACTERISTIC INFORMATION

### Goal in Context

As a data analyst, I want to produce a report on the top N most populated countries in a continent, where I choose N, so that I can compare the largest countries within that continent.

### Scope

Organisation.

### Level

Primary task.

### Preconditions

The world database is running and contains up-to-date country and city data.
The analyst knows which continent they want to report on.

### Success End Condition

A Country Report is provided to the analyst listing the top N countries in the chosen continent, ordered from largest to smallest population.

### Failed End Condition

No report is produced.

### Primary Actor

Analyst.

### Trigger

The analyst requests the top N populated countries for a continent.

## MAIN SUCCESS SCENARIO

1. The analyst requests a report on the top N populated countries in a continent.
2. The analyst provides the continent name and the value of N.
3. The system retrieves the countries in that continent from the database, ordered by population from largest to smallest.
4. The system limits the results to the top N countries.
5. The system displays the Country Report with the columns Code, Name, Continent, Region, Population and Capital.

## EXTENSIONS

2. **N is zero or negative**:
    1. The system informs the analyst that N must be greater than 0 and no report is produced.
2. **The continent does not exist in the database**:
    1. The system displays an empty report with only the column headings.
4. **N is larger than the number of countries in the continent**:
    1. The system displays every country in that continent.
5. **A country has no capital city**:
    1. The system shows a dash (-) in the Capital column.
3. **The database cannot be reached**:
    1. The system informs the analyst that the report could not be produced.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0