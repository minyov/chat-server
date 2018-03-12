import express from 'express';
import bodyParser from 'body-parser';
import fetch from 'node-fetch';

const host = process.env.HOST;
const port = process.env.PORT || 3000;
// http://localhost:8000/link
const link = process.env.LINK;

const app = express();
app.listen(port, host);

const jsonParser = bodyParser.json();
const urlEncoded = bodyParser.urlencoded({ extended: true });
app.use(jsonParser);
app.use(urlEncoded);

app.post('/subscribe', async (req, res) => {
  try {
    const response = await fetch(`${link}/${req.body.username}`, {
      method: 'POST',
    });
    if (response.status > 299) {
      throw new Error('error');
    }
  } catch(e) {
    res.status(500).send();
  }
  res.status(204).send();
});

console.log(`app listens on ${host}:${port}`);
