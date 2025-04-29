import requests
import json
import uuid
import datetime
import random
from decimal import Decimal

# URL of the endpoint
url = "http://localhost:8080/api/v1/statistics/player/stats"

def generate_random_stats():
    """Generates random valid player statistics."""
    year = random.randint(2020, 2024)


    player_data = {
        "playerId": str(random.randint(1, 14)),
        "gameId": str(random.randint(1, 50)),
        "timestamp": datetime.datetime.now().isoformat(),
        "season" : f"{year}-{year + 1}",
        "points": random.randint(0, 50),
        "rebounds": random.randint(0, 25),
        "assists": random.randint(0, 20),
        "steals": random.randint(0, 10),
        "blocks": random.randint(0, 10),
        "fouls": random.randint(0, 6),
        "turnovers": random.randint(0, 10),
        "teamId": random.randint(0, 10),
        "id": f"{random.randint(0, 10000000)}-{random.randint(0, 10000000)}"

    }
    player_data["minutesPlayed"] = str(Decimal(random.randrange(0, 481, 1))/10)  # Convert to string


    return player_data

# Generate and send random stats 10 times
for _ in range(10):  # Send 10 sets of stats
    random_stats = generate_random_stats()

    try:
        response = requests.post(url, headers={"Content-Type": "application/json"}, json=random_stats)
        response.raise_for_status()

        print(f"Inserted stats: {json.dumps(random_stats, indent=4)}")
        print(f"Response: {json.dumps(response.json(), indent=4)}")

    except requests.exceptions.RequestException as e:
        print(f"Error inserting player statistics: {e}")
        if response:
            print(f"Status code: {response.status_code}")
            try:
                print(f"Response body: {json.dumps(response.json(), indent=4)}")
            except json.JSONDecodeError:
                print(f"Response body (not valid JSON): {response.text}")
