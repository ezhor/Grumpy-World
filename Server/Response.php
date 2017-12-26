<?php

// This class will generate the HTTP headers and the body to send to the client
class Response
{

    private $code;
    private $headers;
    private $body;
    private $format;

    // will receive the response code (200 by default), an associative array with the headers, the data for the body,
    // and the format to output the body (retrieved from the request that the client made)
    public function __construct($code = '200', $headers = null, $body = null, $format = 'json')
    {
        $this->code = $code;
        $this->headers = $headers;
        $this->body = $body;
        $this->format = $format;
    }

    public function generate()
    {

        switch ($this->format) {
            case 'json':

                if (!empty($this->body)) {
                    $this->headers['Content-Type'] = "application/json";
                    $this->body = json_encode($this->body);
                }
                break;
            case 'xml':
                if (!empty($this->body)) {
                    $this->headers['Content-Type'] = "text/xml";
                    // WE SHOULD GENERATE XML BODY HERE
//                    if (is_object(serialize($this->body))) {
//                        $this->body = get_object_vars($this->body);
//                    }
//                    $this->body=$this->xml_encode($this->body);
                }
                break;
            //The server does not support the requested format
            case 'unsupported':
                if ($this->body != null) {
                    $this->code = '406';
                    $this->body = null;
                }
        }

        http_response_code($this->code);
        if (isset($this->headers)) {
            foreach ($this->headers as $key => $value) {
                header($key . ': ' . $value);
            }
        }
        if (!empty($this->body)) {
            echo $this->body;
        }
    }


    // https://www.darklaunch.com/2009/05/23/php-xml-encode-using-domdocument-convert-array-to-xml-json-encode
    // http://stackoverflow.com/questions/7609095/is-there-an-xml-encode-like-json-encode-in-php
    private function xml_encode($mixed, $domElement=null, $DOMDocument=null) {
        if (is_null($DOMDocument)) {
            $DOMDocument =new DOMDocument;
            $DOMDocument->formatOutput = true;
            $this->xml_encode($mixed, $DOMDocument, $DOMDocument);
            echo $DOMDocument->saveXML();
        }
        else {
            if (is_array($mixed)) {
                foreach ($mixed as $index => $mixedElement) {
                    if (is_int($index)) {
                        if ($index === 0) {
                            $node = $domElement;
                        }
                        else {
                            $node = $DOMDocument->createElement($domElement->tagName);
                            $domElement->parentNode->appendChild($node);
                        }
                    }
                    else {
                        $plural = $DOMDocument->createElement($index);
                        $domElement->appendChild($plural);
                        $node = $plural;
                        if (!(rtrim($index, 's') === $index)) {
                            $singular = $DOMDocument->createElement(rtrim($index, 's'));
                            $plural->appendChild($singular);
                            $node = $singular;
                        }
                    }

                    $this->xml_encode($mixedElement, $node, $DOMDocument);
                }
            }
            else {
                $domElement->appendChild($DOMDocument->createTextNode($mixed));
            }
        }
    }

}