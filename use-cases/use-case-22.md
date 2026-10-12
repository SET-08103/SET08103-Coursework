# USE CASE: ISSUE #22 The top N populated capital cities in a region where N is provided by the user.


## CHARACTERISTIC INFORMATION

### Goal in Context

As an analyst, I want to see the top N most populated capital cities in a region, where I choose N, so that I can compare major capitals within a region.

### Scope

Organisation

### Level

Primary task.

### Preconditions

Database contains all relevant data related to world, population, countries, continents and cities

### Success End Condition

A report is available for the organisation to view as they wish.

### Failed End Condition

No report is produced.

### Primary Actor

Data Analyst.

### Trigger

A request from an analyst about information on N cities in a region organised by population is requested.

## MAIN SUCCESS SCENARIO

1. Organisation requests information on N cities in a region organised by population.
2. Data Analyst captures name of the cities to get city and region information.
3. Data Analyst extracts name information of N cities and region.
4. Data Analyst provides report to organisation.

## EXTENSIONS

3. **Content does not exist**:
    1. Data Analyst shares to organisation that no content exists.

## SUB-VARIATIONS

None.

## SCHEDULE

**DUE DATE**: Release 1.0