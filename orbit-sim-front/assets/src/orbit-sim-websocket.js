let _orbitSimSocket = null;

class OrbitSimSocket
{
    constructor(elmApp)
    {
        this._elmApp = elmApp;
        this._websocket = new WebSocket("ws://localhost:1337");
        this._websocket.onopen = (e) => this.connectToServer(e);
        _orbitSimSocket = this;
    }

    connectToServer(event)
    {
        this._elmApp.ports.receiveConnectedEvent.send(null);
    }
}