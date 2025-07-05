package collections.blockingQueue;


/*
ВАЖЛИВО:
- BlockingQueue — це потокобезпечна черга з вбудованою блокувальною логікою.
- Методи put() та take() блокують потік автоматично, якщо черга повна або порожня відповідно.
- Не треба писати synchronized, wait/notify, блокування — все робиться під капотом.
- Це значно спрощує реалізацію патерну Producer-Consumer.
- ArrayBlockingQueue — це черга з фіксованим розміром (capacity).

ЧОМУ BlockingQueue КРАЩЕ НІЖ wait/notify?
- Не потрібно явно керувати моніторами (synchronized), викликами wait/notify,
  що зменшує ризик помилок, таких як deadlock або пропущений notify.
- Потоки автоматично блокуються та прокидаються при додаванні/видаленні елементів.
- Код стає простішим для розуміння та підтримки.
- BlockingQueue — це офіційно рекомендований Java-підхід для патерну Producer-Consumer.
*/
public class BlockingQueueDescription {
}
