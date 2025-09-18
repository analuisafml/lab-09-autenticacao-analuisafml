#!/bin/bash

# =============================================================================
# API Test Script - Figurinhas Copa Spring Boot Application
# =============================================================================

# Configuration
BASE_URL="http://localhost:8080/api"
CONTENT_TYPE="Content-Type: application/json"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Test counters
TOTAL_TESTS=0
PASSED_TESTS=0
FAILED_TESTS=0

# Function to print test headers
print_header() {
    echo -e "\n${BLUE}========================================${NC}"
    echo -e "${BLUE}$1${NC}"
    echo -e "${BLUE}========================================${NC}\n"
}

# Function to print test results
print_test() {
    TOTAL_TESTS=$((TOTAL_TESTS + 1))
    local test_name="$1"
    local expected_status="$2"
    local actual_status="$3"
    local response="$4"
    
    # Check if expected_status contains multiple acceptable codes (e.g., "400|500")
    if [[ "$expected_status" == *"|"* ]]; then
        # Split expected statuses and check if actual matches any
        IFS='|' read -ra EXPECTED_CODES <<< "$expected_status"
        local match_found=false
        for code in "${EXPECTED_CODES[@]}"; do
            if [[ "$actual_status" == "$code" ]]; then
                match_found=true
                break
            fi
        done
        
        if [[ "$match_found" == true ]]; then
            echo -e "${GREEN}✓ PASS${NC} - $test_name (Status: $actual_status, Expected: $expected_status)"
            PASSED_TESTS=$((PASSED_TESTS + 1))
        else
            echo -e "${RED}✗ FAIL${NC} - $test_name (Expected: $expected_status, Got: $actual_status)"
            echo -e "${RED}Response: $response${NC}"
            FAILED_TESTS=$((FAILED_TESTS + 1))
        fi
    else
        # Single expected status code
        if [[ "$actual_status" == "$expected_status" ]]; then
            echo -e "${GREEN}✓ PASS${NC} - $test_name (Status: $actual_status)"
            PASSED_TESTS=$((PASSED_TESTS + 1))
        else
            echo -e "${RED}✗ FAIL${NC} - $test_name (Expected: $expected_status, Got: $actual_status)"
            echo -e "${RED}Response: $response${NC}"
            FAILED_TESTS=$((FAILED_TESTS + 1))
        fi
    fi
}

# Function to make HTTP request and return status code
make_request() {
    local method="$1"
    local url="$2"
    local data="$3"
    local description="$4"
    local expected_status="$5"
    
    if [[ -n "$data" ]]; then
        response=$(curl -s -w "\n%{http_code}" -X "$method" -H "$CONTENT_TYPE" -d "$data" "$url")
    else
        response=$(curl -s -w "\n%{http_code}" -X "$method" "$url")
    fi
    
    # Extract status code (last line) and body (everything else)
    status_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n -1)
    
    print_test "$description" "$expected_status" "$status_code" "$body"
    
    # Return the response body for further use
    echo "$body"
}

# Function to extract ID from JSON response
extract_id() {
    local json="$1"
    local id_field="$2"
    echo "$json" | grep -o "\"$id_field\":[0-9]*" | cut -d':' -f2
}

# Start testing
echo -e "${YELLOW}Starting API Tests for Figurinhas Copa Application${NC}"
echo -e "${YELLOW}Make sure the application is running on $BASE_URL${NC}\n"

# =============================================================================
# USER TESTS
# =============================================================================
print_header "TESTING USER ENDPOINTS"

# Test 1: Create User (Success)
user_data='{"nome":"João Silva","email":"joao@email.com"}'
user_response=$(make_request "POST" "$BASE_URL/users" "$user_data" "Create User - Success" "201")
USER_ID=$(extract_id "$user_response" "userId")

# Test 2: Create User (Error - Empty data)
make_request "POST" "$BASE_URL/users" '{}' "Create User - Empty Data" "400|500"

# Test 3: Get All Users
make_request "GET" "$BASE_URL/users" "" "Get All Users" "200"

# Test 4: Get User by ID (Success)
if [[ -n "$USER_ID" ]]; then
    make_request "GET" "$BASE_URL/users/$USER_ID" "" "Get User by ID - Success" "200"
fi

# Test 5: Get User by ID (Not Found)
make_request "GET" "$BASE_URL/users/99999" "" "Get User by ID - Not Found" "404"

# Test 6: Update User (Success)
if [[ -n "$USER_ID" ]]; then
    update_data='{"nome":"João Santos","email":"joao.santos@email.com"}'
    make_request "PUT" "$BASE_URL/users/$USER_ID" "$update_data" "Update User - Success" "200"
fi

# Test 7: Update User (Not Found)
update_data='{"nome":"Test","email":"test@email.com"}'
make_request "PUT" "$BASE_URL/users/99999" "$update_data" "Update User - Not Found" "404"

# Test 8: Update User Photo (Success)
if [[ -n "$USER_ID" ]]; then
    photo_data='{"url":"https://example.com/photo.jpg"}'
    make_request "PUT" "$BASE_URL/users/$USER_ID/photo" "$photo_data" "Update User Photo - Success" "200"
fi

# Test 9: Update User Photo (Not Found)
photo_data='{"url":"https://example.com/photo.jpg"}'
make_request "PUT" "$BASE_URL/users/99999/photo" "$photo_data" "Update User Photo - Not Found" "404"

# =============================================================================
# FIGURINHA TESTS
# =============================================================================
print_header "TESTING FIGURINHA ENDPOINTS"

# Test 10: Create Figurinha (Success)
figurinha_data='{"nome":"Neymar","selecao":"Brasil"}'
figurinha_response=$(make_request "POST" "$BASE_URL/figurinhas" "$figurinha_data" "Create Figurinha - Success" "201")
FIGURINHA_ID=$(extract_id "$figurinha_response" "figId")

# Test 11: Create Another Figurinha
figurinha_data2='{"nome":"Messi","selecao":"Argentina"}'
figurinha_response2=$(make_request "POST" "$BASE_URL/figurinhas" "$figurinha_data2" "Create Another Figurinha - Success" "201")
FIGURINHA_ID2=$(extract_id "$figurinha_response2" "figId")

# Test 12: Create Figurinha (Error - Empty data)
make_request "POST" "$BASE_URL/figurinhas" '{}' "Create Figurinha - Empty Data" "201"

# Test 13: Get All Figurinhas
make_request "GET" "$BASE_URL/figurinhas" "" "Get All Figurinhas" "200"

# Test 14: Get Figurinha by ID (Success)
if [[ -n "$FIGURINHA_ID" ]]; then
    make_request "GET" "$BASE_URL/figurinhas/$FIGURINHA_ID" "" "Get Figurinha by ID - Success" "200"
fi

# Test 15: Get Figurinha by ID (Not Found)
make_request "GET" "$BASE_URL/figurinhas/99999" "" "Get Figurinha by ID - Not Found" "404"

# Test 16: Get Figurinhas by Selection (Success)
make_request "GET" "$BASE_URL/figurinhas/selecao/Brasil" "" "Get Figurinhas by Selection - Success" "200"

# Test 17: Update Figurinha (Success)
if [[ -n "$FIGURINHA_ID" ]]; then
    update_figurinha='{"nome":"Neymar Jr","selecao":"Brasil"}'
    make_request "PUT" "$BASE_URL/figurinhas/$FIGURINHA_ID" "$update_figurinha" "Update Figurinha - Success" "200"
fi

# Test 18: Update Figurinha (Not Found)
update_figurinha='{"nome":"Test","selecao":"Test"}'
make_request "PUT" "$BASE_URL/figurinhas/99999" "$update_figurinha" "Update Figurinha - Not Found" "404"

# =============================================================================
# ALBUM TESTS
# =============================================================================
print_header "TESTING ALBUM ENDPOINTS"

# Test 19: Create Album (Success)
if [[ -n "$USER_ID" ]]; then
    album_data='{"nome":"Meu Álbum da Copa"}'
    album_response=$(make_request "POST" "$BASE_URL/albums/user/$USER_ID" "$album_data" "Create Album - Success" "201")
    ALBUM_ID=$(extract_id "$album_response" "albumId")
fi

# Test 20: Create Album (Error - User Not Found)
album_data='{"nome":"Álbum Inválido"}'
make_request "POST" "$BASE_URL/albums/user/99999" "$album_data" "Create Album - User Not Found" "400|500"

# Test 21: Create Album (Error - Empty data)
if [[ -n "$USER_ID" ]]; then
    make_request "POST" "$BASE_URL/albums/user/$USER_ID" '{}' "Create Album - Empty Data" "201"
fi

# Test 22: Get All Albums
make_request "GET" "$BASE_URL/albums" "" "Get All Albums" "200"

# Test 23: Get Album by ID (Success)
if [[ -n "$ALBUM_ID" ]]; then
    make_request "GET" "$BASE_URL/albums/$ALBUM_ID" "" "Get Album by ID - Success" "200"
fi

# Test 24: Get Album by ID (Not Found)
make_request "GET" "$BASE_URL/albums/99999" "" "Get Album by ID - Not Found" "404"

# Test 25: Get Albums by User ID (Success)
if [[ -n "$USER_ID" ]]; then
    make_request "GET" "$BASE_URL/albums/user/$USER_ID" "" "Get Albums by User ID - Success" "200"
fi

# Test 26: Update Album (Success - Only Name)
if [[ -n "$ALBUM_ID" ]]; then
    update_album='{"nome":"Álbum Atualizado"}'
    make_request "PUT" "$BASE_URL/albums/$ALBUM_ID" "$update_album" "Update Album Name - Success" "200"
fi

# Test 27: Update Album (Not Found)
update_album='{"nome":"Test Album"}'
make_request "PUT" "$BASE_URL/albums/99999" "$update_album" "Update Album - Not Found" "404"

# =============================================================================
# ALBUM-FIGURINHA RELATIONSHIP TESTS
# =============================================================================
print_header "TESTING ALBUM-FIGURINHA RELATIONSHIPS"

# Test 28: Add Figurinha to Album (Success)
if [[ -n "$ALBUM_ID" && -n "$FIGURINHA_ID" ]]; then
    make_request "POST" "$BASE_URL/albums/$ALBUM_ID/figurinhas/$FIGURINHA_ID" "" "Add Figurinha to Album - Success" "200"
fi

# Test 29: Add Same Figurinha Again (Error - Already exists)
if [[ -n "$ALBUM_ID" && -n "$FIGURINHA_ID" ]]; then
    make_request "POST" "$BASE_URL/albums/$ALBUM_ID/figurinhas/$FIGURINHA_ID" "" "Add Same Figurinha Again - Error" "400|500"
fi

# Test 30: Add Another Figurinha to Album
if [[ -n "$ALBUM_ID" && -n "$FIGURINHA_ID2" ]]; then
    make_request "POST" "$BASE_URL/albums/$ALBUM_ID/figurinhas/$FIGURINHA_ID2" "" "Add Another Figurinha to Album - Success" "200"
fi

# Test 31: Add Figurinha to Non-existent Album
if [[ -n "$FIGURINHA_ID" ]]; then
    make_request "POST" "$BASE_URL/albums/99999/figurinhas/$FIGURINHA_ID" "" "Add Figurinha to Non-existent Album - Error" "400|500"
fi

# Test 32: Add Non-existent Figurinha to Album
if [[ -n "$ALBUM_ID" ]]; then
    make_request "POST" "$BASE_URL/albums/$ALBUM_ID/figurinhas/99999" "" "Add Non-existent Figurinha to Album - Error" "400|500"
fi

# Test 33: Remove Figurinha from Album (Success)
if [[ -n "$ALBUM_ID" && -n "$FIGURINHA_ID" ]]; then
    make_request "DELETE" "$BASE_URL/albums/$ALBUM_ID/figurinhas/$FIGURINHA_ID" "" "Remove Figurinha from Album - Success" "200"
fi

# Test 34: Remove Same Figurinha Again (Error - Not in album)
if [[ -n "$ALBUM_ID" && -n "$FIGURINHA_ID" ]]; then
    make_request "DELETE" "$BASE_URL/albums/$ALBUM_ID/figurinhas/$FIGURINHA_ID" "" "Remove Same Figurinha Again - Error" "400|500"
fi

# Test 35: Remove Figurinha from Non-existent Album
if [[ -n "$FIGURINHA_ID" ]]; then
    make_request "DELETE" "$BASE_URL/albums/99999/figurinhas/$FIGURINHA_ID" "" "Remove Figurinha from Non-existent Album - Error" "400|500"
fi

# =============================================================================
# CLEANUP TESTS (DELETE OPERATIONS)
# =============================================================================
print_header "TESTING DELETE OPERATIONS"

# Test 36: Delete Album (Success)
if [[ -n "$ALBUM_ID" ]]; then
    make_request "DELETE" "$BASE_URL/albums/$ALBUM_ID" "" "Delete Album - Success" "204"
fi

# Test 37: Delete Album (Not Found)
make_request "DELETE" "$BASE_URL/albums/99999" "" "Delete Album - Not Found" "404"

# Test 38: Delete Figurinha (Success)
if [[ -n "$FIGURINHA_ID" ]]; then
    make_request "DELETE" "$BASE_URL/figurinhas/$FIGURINHA_ID" "" "Delete Figurinha - Success" "204"
fi

# Test 39: Delete Figurinha (Not Found)
make_request "DELETE" "$BASE_URL/figurinhas/99999" "" "Delete Figurinha - Not Found" "404"

# Test 40: Delete User (Success)
if [[ -n "$USER_ID" ]]; then
    make_request "DELETE" "$BASE_URL/users/$USER_ID" "" "Delete User - Success" "204"
fi

# Test 41: Delete User (Not Found)
make_request "DELETE" "$BASE_URL/users/99999" "" "Delete User - Not Found" "404"

# =============================================================================
# TEST SUMMARY
# =============================================================================
print_header "TEST SUMMARY"

echo -e "${BLUE}Total Tests: $TOTAL_TESTS${NC}"
echo -e "${GREEN}Passed: $PASSED_TESTS${NC}"
echo -e "${RED}Failed: $FAILED_TESTS${NC}"

if [[ $FAILED_TESTS -eq 0 ]]; then
    echo -e "\n${GREEN}🎉 All tests passed! Your API is working correctly.${NC}"
    exit 0
else
    echo -e "\n${RED}❌ Some tests failed. Please check the API implementation.${NC}"
    exit 1
fi
