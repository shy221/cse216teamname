<div id="ShowQR" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">Post a New Event</h4>
            </div>
            <div class="modal-body">
                <label for="ShowQR-title">Event Title</label>
                <input class="form-control" type="text" id="ShowQR-title" />
                <input type = "text" id = "qr-data" onchange = "generateQR()">
                <label for="ShowQR-message">Event Detail</label>
                <textarea class="form-control" id="ShowQR-message"></textarea>
                <label for="ShowQR-link">Link</label>
                <input class="form-control" type="text" id="ShowQR-link" />
                <label for="ShowQR-attachment">Attachment</label>
                <input class="form-control-file" type="file" accept=".pdf" id="ShowQR-attachment" />
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="ShowQR-OK">OK</button>
                <button type="button" class="btn btn-default" id="ShowQR-Close">Close</button>
            </div>
        </div>
    </div>
</div>