import argparse
import http.client  # Fix import
import sys
import time         # Add this import
import json         # Add this import

GENERIC_NOTIFICATION_ENDPOINT = "/generic"
POST_NOTIFICATION_ENDPOINT = "/newNotification"

NOTIFICATION_BODY = {
    "notification": "This is for all"
}

def read_arguments():
    parser = argparse.ArgumentParser(description="A script to simulate a client load on a server.")
    parser.add_argument("--server", type=str, required=True, help="Input a server address")
    parser.add_argument("--runner", type=str, required=True, help="Number of clients to run")
    
    args = parser.parse_args()
    
    print(f"Server address: {args.server}")
    print(f"Number of clients: {args.runner}")
    
    global server_address, num_clients
    server_address, num_clients = args.server, int(args.runner)
    
def initialize_clients():
    print(f"Initializing {num_clients} clients to connect to {server_address}...")
    global clients
    clients = []
    for i in range(num_clients):
        client = http.client.HTTPConnection(server_address)
        client.request("GET", GENERIC_NOTIFICATION_ENDPOINT)
        clients.append(client)
    print(f"{num_clients} clients initialized successfully.")
    
def send_request(client):
    try:
        start_time = time.time()
        response = client.getresponse()
        end_time = time.time()    # Fix here
        print(f"Response from server: {response.status} {response.reason} in {end_time - start_time:.2f} seconds")
    except Exception as e:
        print(f"Error sending request: {e}")

def run_clients():
    import threading
    for client in clients:
        thread = threading.Thread(target=send_request, args=(client,))  # Fix: add 'target='
        thread.start()
        
def send_notification():
    print("Sending notification to server...")
    try:
        notification_client = http.client.HTTPConnection(server_address)
        headers = {"Content-type": "application/json"}
        notification_client.request(
            "POST",
            POST_NOTIFICATION_ENDPOINT,
            body=json.dumps(NOTIFICATION_BODY),
            headers=headers
        )
        response = notification_client.getresponse()
        print(f"Notification response: {response.status} {response.reason}")
    except Exception as e:
        print(f"Error sending notification: {e}")
    finally:
        notification_client.close()

def main():
    read_arguments()
    initialize_clients()
    run_clients()
    send_notification()
    

if __name__ == "__main__":
    main()